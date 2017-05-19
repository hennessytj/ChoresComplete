
package chorescomplete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Hennessy
 * @version 1.0
 */
public class ChoresList
{
    private ArrayList<Chore> chores;
    private final String name;
    
    @SuppressWarnings({"ConvertToTryWithResources", "ResultOfObjectAllocationIgnored"})
    public ChoresList(String name)
    {
        this.name = name;
        buildChoresList();
    }
    
    public ArrayList<Chore> getChoresList()
    { return chores; }
    
    public void save()
    {
        saveChoreListState(); 
        updateLastStartedLog();
    }
    
    /**************************************************************
    * Private/Helper methods
    ***************************************************************/
    
    @SuppressWarnings({"ResultOfObjectAllocationIgnored", "ConvertToTryWithResources"})
    private void saveChoreListState()
    {
        String path = "src/users/" + name + "/chores/" + today(false) + ".txt";
        
        try
        {
            // Overwrite existing file write new date
            FileWriter fw = new FileWriter(new File(path));
            for (Chore c : chores)
            {
                fw.write(c.getChoreName() + "," + 
                         c.getValue()     + "," +
                         c.isComplete()   +
                        System.getProperty("line.separator"));
            }
            fw.close();
        }
        catch (IOException ex)
        {
            String message = "ChoresList.java: " + path +  " write error. " + 
                    ex.toString();
            new Logger("errors.txt", message);
        }
    }
    
    /**
     * Builds the chore list from file.  If the application has been started
     * already today each chore completion status is restored from earlier start.
     * If this application has not been started today, chore completion status 
     * is assigned false.
     * @param day day of week all lowercase letters
     * 
     */
    @SuppressWarnings({"ResultOfObjectAllocationIgnored", "Convert2Diamond", 
        "ConvertToTryWithResources"})
    private void buildChoresList()
    {
        assert name != null;
        
        chores = new ArrayList<Chore>();

        boolean alreadyStartedToday = getLastStart().equals(today(true));

        // e.g., users/ian/days/friday.txt
        String path = "src/users/" + name + "/chores/" + today(false) + ".txt";
        try
        {
            File file = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] tokens  = line.split(",");
                String choreName = tokens[0];
                int choreValue   = Integer.parseInt(tokens[1]);
                boolean completionStatus;
                if (alreadyStartedToday)
                {
                    completionStatus = Boolean.parseBoolean(tokens[2]);
                }
                else
                {
                    completionStatus = false;
                }
                chores.add(new Chore(choreName, choreValue, completionStatus));
            }
            reader.close();
        }
        catch (IOException | NumberFormatException ex)
        {
            String message = "ChoresList.java: file read error in"
                    + " addChoresToList " + ex.toString();
            new Logger("errors.txt", message);
        }
    }
    
    @SuppressWarnings({"ConvertToTryWithResources", "ResultOfObjectAllocationIgnored"})
    private String getLastStart()
    {
        String dateOfLastStart = null;
        
        try
        {
            // example to append to top line of file
            String path = "src/logs/last_started.txt";
            File file = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            dateOfLastStart = reader.readLine();
            //System.out.println(dateOfLastStart);
            reader.close();
        }
        catch (IOException ex)
        {
            String message = "ChoresList.java: last_started.txt read error. " + 
                    ex.toString();
            new Logger("errors.txt", message);
        }
        
        return dateOfLastStart;
    }
    
    /**
     * Returns the day of the week.  If formatted is true it returns
     * day of the week followed by the date.  If formatted is not true
     * it returns day of week.
     * @return string that is either formatted or unformatted date
     */
    private String today(boolean formatted)
    {
        String today;
        if (formatted)
        {
            SimpleDateFormat dft = new SimpleDateFormat("EEEE MM/dd/yyyy");
            today = dft.format(new Date());
        }
        else
        {
            Date day = new Date();
            SimpleDateFormat dft = new SimpleDateFormat("EEEE"); 
            today = dft.format(day).toLowerCase();
        }
        return today;
    }
         
    /**
     * Keeps date of last execution updated.  Very important to loading process.
     * This date is used to determine whether to load a new day or to load state
     * from a previously started one.
     */
    @SuppressWarnings({"ResultOfObjectAllocationIgnored", 
        "ConvertToTryWithResources"})
    private void updateLastStartedLog()
    {
        String path = "src/logs/last_started.txt";
        
        try
        {
            // Overwrite existing file write new date
            FileWriter fw = new FileWriter(new File(path));
            fw.write(today(true) + System.getProperty("line.separator"));
            fw.close();
        }
        catch (IOException ex)
        {
            String message = "ChoresList.java: last_started.txt write error. " + 
                    ex.toString();
            new Logger("errors.txt", message);
        }
    }   
}
