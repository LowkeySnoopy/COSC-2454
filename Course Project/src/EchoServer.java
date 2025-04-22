import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * COSC 2454 – DDS Project
 * Multi-threaded EchoServer with peer awareness.
 * Handles client connections and launches EchoThreads for each.
 */
public class EchoServer {

	/** The server will listen on this port for client connections */
	public static final int SERVER_PORT = 8754;

	public static void main(String[] args) {
		// Ensure config file argument is provided
		if (args.length != 1) {
			System.err.println("Usage: java EchoServer <server_config.json>");
			System.exit(-1);
		}

		String configFile = args[0];
		List<ServerInfo> peerServers = null;

		try {
			// Load peer server information (excluding this server's port)
			ServerRegistry registry = new ServerRegistry(configFile, SERVER_PORT);
			peerServers = registry.getPeers();

			// Start listening on the designated port
			final ServerSocket serverSock = new ServerSocket(SERVER_PORT);
			System.out.println("Server started on port " + SERVER_PORT);
			System.out.println("Peer servers loaded: " + peerServers);
			System.out.println("Waiting for client connections...\n");

			// Main loop: accept and handle client connections
			while (true) {
				Socket sock = serverSock.accept();                        // Accept client
				EchoThread thread = new EchoThread(sock, peerServers);   // Handle in thread
				thread.start();                                          // Launch it
			}

		} catch (Exception e) {
			System.err.println("Server error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}
}
