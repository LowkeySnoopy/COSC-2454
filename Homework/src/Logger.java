import java.io.*;
import java.time.*;
import java.time.format.*;

public class Logger{
    private String filepath;
    private static Logger log =new Logger();
    private Logger () {
    }
    public static Logger getInstance() {
        return log;
    }
    //allows user to write on log entry
    public void writelog(String newLogEntry) throws IOException{
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        BufferedWriter w= new BufferedWriter(new FileWriter(filepath, true));
        w.write(dtf.format(now)+"-"+newLogEntry+"\n");
        w.close();
        System.out.println("entry has been appended to file");
    }
    //creates the log entry
    public void createLog(String filepath)throws IOException{
        File out=new File (filepath);
        boolean l= out.createNewFile();
        System.out.println("file created");
        this.filepath=filepath;
    }
    //deletes the log entry
    public void deleteLog(String filepath) throws IOException{
        new FileWriter(filepath,false).close();
        System.out.println("file deleted");
    }
}


