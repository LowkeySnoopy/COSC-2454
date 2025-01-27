import java.io.IOException;

public class LoggerDemo {

    public static void main(String[] args) throws IOException{
        Logger n= Logger.getInstance();
        //log creator
        n.createLog("logfile.txt");
        // calls the writing log function
        n.writelog("User Lanard Johnson logged in 30 minutes ago");
        // call the deleting log function
        n.deleteLog("logfile.txt");
    }
}
