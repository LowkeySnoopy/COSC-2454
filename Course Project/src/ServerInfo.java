public class ServerInfo {
    public String ip;
    public int port;

    public ServerInfo() {}  // Needed for JSON parsing

    public ServerInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String toString() {
        return ip + ":" + port;
    }
}
