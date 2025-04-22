import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * EchoClient for interacting with a distributed linked list server.
 * Accepts commands like: ADD <item>, DELETE <item>, VIEW, EXIT
 *
 * COSC 2454 – DDS Project
 */
public class EchoClient {

	public static void main(String[] args) {
		// Validate args
		if (args.length != 2) {
			System.err.println("Usage: java EchoClient <Server IP> <Server Port>");
			System.exit(-1);
		}

		try {
			// Setup connection
			String serverIP = args[0];
			int serverPort = Integer.parseInt(args[1]);
			Socket socket = new Socket(serverIP, serverPort);

			System.out.println("Connected to " + serverIP + " on port " + serverPort);
			System.out.println("LocalPort number is: " + socket.getLocalPort());

			// Setup I/O
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

			boolean keepRunning = true;
			while (keepRunning) {
				System.out.print("\nEnter command (ADD item, DELETE item, VIEW, EXIT):\n > ");
				String userInput = console.readLine().trim();

				if (userInput.isEmpty()) continue;

				String[] parts = userInput.split(" ", 2);
				String operation = parts[0].toUpperCase();
				String value = (parts.length > 1) ? parts[1] : "";

				Message msg = new Message(operation, value);
				output.writeObject(msg);

				// Read response
				Message response = (Message) input.readObject();
				System.out.println("Server: " + response.value);

				if (operation.equals("EXIT")) {
					keepRunning = false;
				}
			}

			// Cleanup
			socket.close();
			System.out.println("Connection closed.");

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}
}
