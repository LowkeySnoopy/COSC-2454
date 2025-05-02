import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * EchoClient for interacting with a distributed server.
 * Supports ADD, DELETE, INSERT, VIEW, COMMIT, ROLLBACK, LOG, and EXIT commands.
 *
 * COSC 2454 – DDS Project
 */
public class EchoClient {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: java EchoClient <Server IP> <Server Port>");
			System.exit(-1);
		}

		try {
			String serverIP = args[0];
			int serverPort = Integer.parseInt(args[1]);
			Socket socket = new Socket(serverIP, serverPort);

			System.out.println("Connected to server at " + serverIP + ":" + serverPort);

			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

			boolean keepRunning = true;
			while (keepRunning) {
				String userInput = readCommandPrompt(console);
				if (userInput.isEmpty()) continue;

				String[] parts = userInput.split(" ", 2);
				String operation = parts[0].toUpperCase();
				String value = (parts.length > 1) ? parts[1] : "";

				Message msg = new Message(operation, value);
				output.writeObject(msg);

				Message response = (Message) input.readObject();
				System.out.println("Server: " + response.value);

				if (operation.equals("EXIT")) {
					keepRunning = false;
				}
			}

			socket.close();
			System.out.println("Connection closed.");

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	private static String readCommandPrompt(BufferedReader in) throws Exception {
		System.out.println("\nEnter a command:");
		System.out.println("ADD <item>");
		System.out.println("DELETE <item>");
		System.out.println("INSERT <index>,<item>");
		System.out.println("VIEW");
		System.out.println("COMMIT");
		System.out.println("ROLLBACK");
		System.out.println("LOG client");
		System.out.println("LOG server");
		System.out.println("EXIT");
		System.out.print(" > ");
		return in.readLine().trim();
	}
}
