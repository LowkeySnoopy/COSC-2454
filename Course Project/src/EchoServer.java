import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * COSC 2454 – DDS Project
 * Multi-threaded EchoServer with dynamic port assignment from config file name.
 * Handles client connections and launches EchoThreads for each.
 */
public class EchoServer {

	public static int SERVER_PORT = -1;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java EchoServer <server_config.json>");
			System.exit(-1);
		}

		String configFile = args[0];
		SERVER_PORT = extractMyPort(configFile);

		List<ServerInfo> peerServers = null;

		try {
			ServerRegistry registry = new ServerRegistry(configFile, SERVER_PORT);
			peerServers = registry.getPeers();

			// Attempt to sync with peers before accepting clients
			DistributedLinkedList syncedList = syncWithPeer(peerServers);

			final ServerSocket serverSock = new ServerSocket(SERVER_PORT);
			System.out.println("Server started on port " + SERVER_PORT);
			System.out.println("Peer servers loaded: " + peerServers);
			System.out.println("Waiting for client connections...\n");

			while (true) {
				Socket sock = serverSock.accept();
				EchoThread thread = new EchoThread(sock, peerServers, syncedList);
				thread.start();
			}

		} catch (Exception e) {
			System.err.println("Server error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	/**
	 * Extracts the port number from the filename (e.g., server_8754.json → 8754).
	 */
	private static int extractMyPort(String configFile) {
		try {
			String[] parts = configFile.replace(".json", "").split("_");
			return Integer.parseInt(parts[1]);
		} catch (Exception e) {
			System.err.println("Failed to extract port from config file. Defaulting to 8754.");
			return 8754;
		}
	}

	/**
	 * Attempts to sync with a peer server to pull the latest LinkedList.
	 * If all peers fail, returns a new empty list.
	 */
	private static DistributedLinkedList syncWithPeer(List<ServerInfo> peers) {
		for (ServerInfo peer : peers) {
			try (Socket sock = new Socket(peer.ip, peer.port);
				 ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
				 ObjectInputStream in = new ObjectInputStream(sock.getInputStream())) {

				out.writeObject(new Message("SYNC", ""));
				Message response = (Message) in.readObject();

				DistributedLinkedList synced = new DistributedLinkedList();
				String raw = response.value.replaceAll("[\\[\\]]", "");
				for (String item : raw.split(",")) {
					if (!item.trim().isEmpty()) {
						synced.add(item.trim());
					}
				}

				System.out.println("Synchronized list from peer " + peer + ": " + synced);
				return synced;

			} catch (Exception e) {
				System.err.println("Could not sync from " + peer + ": " + e.getMessage());
			}
		}

		System.out.println("No peers responded. Starting with empty list.");
		return new DistributedLinkedList();
	}
}
