
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
    { 
        ArrayList<Chore> safeCopy = new ArrayList<>();
        for (Chore c : chores)
        {
            safeCopy.add(c);
        }
        return safeCopy; 
    }
    
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

        // if application has already been started today use completion
        // status values from previous session
        boolean alreadyStartedToday = getLastStart().equals(today(true));

        // e.g., users/ian/days/friday.txt
        String path = "src/users/" + name + "/chores/" + today(false) + ".txt";
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line;
            while ((line = reader.readLine()) != null)
            {   // use commas to delimit tokens
                String[] tokens  = line.split(",");
                String choreName = tokens[0];
                int choreValue   = Integer.parseInt(tokens[1]);
                boolean completionStatus;  // has chore been completed today?
                if (alreadyStartedToday)
                {
                    completionStatus = Boolean.parseBoolean(tokens[2]);
                }
                else
                {   // if application has not been started today no chores have
                    // been completed yet so mark false
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
            String path = "src/logs/last_started.txt";
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            dateOfLastStart = reader.readLine();
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
        {   // e.g., Monday 01/01/2000
            SimpleDateFormat dft = new SimpleDateFormat("EEEE MM/dd/yyyy");
            today = dft.format(new Date());
        }
        else
        {   // e.g., Monday
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
        {   // overwrite existing file to write change
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
