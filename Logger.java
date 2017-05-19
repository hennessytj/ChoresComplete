// TODO: Look into synchronizing access to files to prevent unpredictable
//       access (race conditions)
package chorescomplete;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger 
{
    private FileWriter fw;
    
    /**
     * Appends message to appropriate log.
     * @param fileName log file to write 
     * @param message specialized description for log file entry
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public Logger(String fileName, String message)
    {
        assert fileName != null;
        // Only write out messages to the below logfiles
        switch (fileName)
        {
            case "starts.txt":
            case "actions.txt":
            case "errors.txt":
                try 
                {
                    String path = "src/logs/" + fileName; // logs/<name>.txt
                    fw = new FileWriter(new File(path), true);  // append to file
                    // write a line with the system appropriate new line char
                    fw.write(message + " " + new Date() + 
                            System.getProperty("line.separator"));
                    fw.close();                       // flushes before close         
                } 
                catch (IOException ex) 
                {
                    String msg = "Logger.java: file error. " + ex.toString();
                    new Logger("errors.txt", msg);
                }     
        }
    }
}
