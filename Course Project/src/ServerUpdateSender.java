import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Sends a Message to all other servers in the network.
 * Used for synchronizing updates across the DDS system.
 *
 * COSC 2454 – DDS Project
 */
public class ServerUpdateSender {

    private final List<ServerInfo> peerServers;

    public ServerUpdateSender(List<ServerInfo> peerServers) {
        this.peerServers = peerServers;
    }

    /**
     * Sends a message to all peer servers.
     *
     * @param message The Message object to send
     */
    public void broadcastUpdate(Message message) {
        for (ServerInfo peer : peerServers) {
            try (Socket socket = new Socket(peer.ip, peer.port);
                 ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

                output.writeObject(message);
                System.out.println("[Broadcast] Sent to " + peer + ": " + message);

            } catch (Exception e) {
                System.err.println("[Broadcast] Failed to send to " + peer + ": " + e.getMessage());
            }
        }
    }
}
