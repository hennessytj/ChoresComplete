
package chorescomplete;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 *           Returns Method                   Description
 * -------------------------------------------------------------------
 *           String  getName()                Returns users name
 * ArrayList<Chore>  getChoreList()           Returns chores for today
 *              int  getAmountEarned()        Returns users cumulative balance
 *             void  incAmountEarned(int inc) Used to add money for a completed
 *                                            chore by user
 *             void  decAmountEarned(int dec) Used to subtract money from users
 *                                            account
 *             void  save()                   Saves users balance to disk              
 * 
 * 
 * @author Hennessy
 */
public class Person
{
    private final String name;    
    private Account earnings;
    private ChoresList dailyChores;      // Users chores for the day
    private final int startBalance;      // If balance is unchanged, this
                                         // will prevent extra write to file
    
    @SuppressWarnings({"Convert2Diamond", "ResultOfObjectAllocationIgnored"})
    public Person(String name)
    {
        if (name == null)
        {
            String message = "Person.java: name is null.";
            new Logger("errors.txt", message);
            System.exit(0);
        }
        
        this.name = name;
        
        earnings = new Account(name);
        
        startBalance = earnings.getBalance();
        
        dailyChores = new ChoresList(name);
    }
    
    /**
     * Returns persons name.
     * @return string corresponding to persons name
     */
    public String getName()
    { return name; }
    
    /**
     * Returns the ArrayList of chore objects for the given user.
     * @return ArrayList of chore objects
     */
    public ArrayList<Chore> getChoreList()
    { return dailyChores.getChoresList(); }
      
    /**
     * Returns amount earned by completing chores.
     * @return integer amount earned by person
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public int getAmountEarned()
    { 
        int amount;
        if (earnings == null)
        {
            String message = "Person.java: in getAmountEarned() earnings "
                    + "is null, should not call instance method on null "
                    + "object.";
            new Logger("errors.txt", message);
            amount = 0;
        }
        else 
            amount = earnings.getBalance();
        
        return amount; 
    }
    
    /**
     * Used when a chore has been completed.
     * @param inc value of chore completed
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void incAmountEarned(int inc)
    {
        if (inc <= 0)
        {
            String message = "Person.java: increment less than or equal to 0.";
            new Logger("errors.txt", message);
            return;
        }
        
        earnings.deposit(inc);
    } 
    
    /**
     * Used to subtract value of chore which was mistakingly
     * added into total as completed.
     * @param dec value of chore 
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void decAmountEarned(int dec)
    {   // total never goes negative
        if (dec <= 0)
        {
            String message = "Person.java: decrement amount is negative.";
            new Logger("errors.txt", message);
        }
        
        earnings.withdraw(dec);
    }
    
    /**
     * Saves users earnings to disk.  Only writes out a balance if it
     * has changed.
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void save()
    {
        if (earnings == null)
        {
            String message = "Person.java: in save() earnings is null, "
                    + "should not call instance method on null object.";
            new Logger("errors.txt", message);
        }
        
        int currentBalance = earnings.getBalance();
        // Only write balance if it has changed
        if (startBalance != currentBalance)
        {
            earnings.writeBalanceToFile();
        }
        
        dailyChores.save();
    }
    
    @Override
    public String toString()
    { return name + "'s amount earned: " + getAmountEarned(); }
    
   /**************************************************************
    * Private/Helper methods
    ***************************************************************/
    
    /**
     * Simple helper method to get current date formatted and returned as
     * string.
     * @return date formatted string, e.g. Monday 01/24/1986
     */
    private String getDayAndDate()
    {
        SimpleDateFormat dft = new SimpleDateFormat("EEEE MM/dd/yyyy");
        return dft.format(new Date());
    } 
}