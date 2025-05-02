import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads peer server info manually from a JSON config file.
 *
 * Format example:
 * [
 *   { "ip": "127.0.0.1", "port": 8754 },
 *   { "ip": "127.0.0.1", "port": 8755 }
 * ]
 */
public class ServerRegistry {

    private final List<ServerInfo> servers;

    public ServerRegistry(String configPath, int myPort) {
        servers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(configPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("{") && line.endsWith("},")) {
                    line = line.substring(0, line.length() - 1); // remove trailing comma
                } else if (line.startsWith("{") && line.endsWith("}")) {
                    // okay
                } else {
                    continue;
                }

                // Extract values manually
                String[] tokens = line.replace("{", "").replace("}", "").split(",");
                String ip = "";
                int port = -1;

                for (String token : tokens) {
                    String[] pair = token.trim().replace("\"", "").split(":");
                    if (pair.length == 2) {
                        if (pair[0].trim().equals("ip")) {
                            ip = pair[1].trim();
                        } else if (pair[0].trim().equals("port")) {
                            port = Integer.parseInt(pair[1].trim());
                        }
                    }
                }

                if (!ip.isEmpty() && port != myPort) {
                    servers.add(new ServerInfo(ip, port));
                }
            }

        } catch (Exception e) {
            System.err.println("Failed to load config file: " + e.getMessage());
        }
    }

    public List<ServerInfo> getPeers() {
        return servers;
    }
}
