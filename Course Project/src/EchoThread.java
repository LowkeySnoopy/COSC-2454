import java.lang.Thread;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * EchoThread handles communication with one client or peer server.
 * It supports distributed updates, logging, commit/rollback, and log viewing.
 */
public class EchoThread extends Thread {

	private final Socket socket;
	private final DistributedLinkedList distributedList;
	private final List<ServerInfo> peerServers;

	public EchoThread(Socket socket) {
		this(socket, null);
	}

	public EchoThread(Socket socket, List<ServerInfo> peerServers) {
		this.socket = socket;
		this.peerServers = peerServers;
		this.distributedList = new DistributedLinkedList();
	}

	@Override
	public void run() {
		try {
			System.out.println("Connection from " + socket.getInetAddress() + ":" + socket.getPort());

			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

			Message msg;
			boolean isRunning = true;

			while (isRunning) {
				msg = (Message) input.readObject();

				boolean isClient = !msg.fromServer;
				String operation = msg.operation.toUpperCase();
				String value = msg.value;

				System.out.println("[" + socket.getInetAddress() + "] Operation: " + operation + ", Value: " + value);

				String response;

				switch (operation) {
					case "ADD":
						distributedList.add(value);
						response = "Added '" + value + "'";
						LogWriter.logUpdate(socket.getInetAddress(), operation, value, !isClient);
						if (isClient) broadcastToPeers(msg);
						break;

					case "DELETE":
						if (distributedList.delete(value)) {
							response = "Deleted '" + value + "'";
						} else {
							response = "Item '" + value + "' not found";
						}
						LogWriter.logUpdate(socket.getInetAddress(), operation, value, !isClient);
						if (isClient) broadcastToPeers(msg);
						break;

					case "VIEW":
						response = "Current list: " + distributedList.toString();
						break;

					case "COMMIT":
						boolean committed = DiskManager.commit(distributedList.getData());
						response = committed ? "List committed to disk." : "Commit failed.";
						break;

					case "ROLLBACK":
						List<String> rolledBack = DiskManager.rollback();
						if (rolledBack != null) {
							distributedList.setData(rolledBack);
							response = "List rolled back to last committed version.";
						} else {
							response = "Rollback failed or no previous version exists.";
						}
						break;

					case "LOG":
						response = readLogFile(value.trim().toLowerCase());
						break;

					case "EXIT":
						response = "Goodbye.";
						isRunning = false;
						break;

					default:
						response = "Unknown operation: " + operation;
				}

				output.writeObject(new Message("SERVER", response));
			}

			System.out.println("Connection closed from " + socket.getInetAddress());
			socket.close();

		} catch (Exception e) {
			System.err.println("Thread error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void broadcastToPeers(Message msg) {
		if (peerServers == null || peerServers.isEmpty()) return;
		msg.fromServer = true;
		ServerUpdateSender sender = new ServerUpdateSender(peerServers);
		sender.broadcastUpdate(msg);
	}

	private String readLogFile(String type) {
		String filename;

		switch (type) {
			case "client":
				filename = "client_updates.log";
				break;
			case "server":
				filename = "server_updates.log";
				break;
			default:
				return "Usage: LOG client OR LOG server";
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			StringBuilder content = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
			return content.length() == 0 ? "Log is empty." : content.toString();
		} catch (Exception e) {
			return "Unable to read log file: " + e.getMessage();
		}
	}
}
