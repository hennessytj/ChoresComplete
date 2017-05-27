
package chorescomplete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *           Returns Method                   Description
 * -------------------------------------------------------------------
 *                   Account(String name)     Take user name and create account
 *              int  getBalance()             Returns users balance
 *           String  getName()                Return account owners name
 *             void  deposit(int amount)      Increment balance by amount
 *             void  withdraw(int amount)     Decrement balance by amount
 *             void  writeBalanceToFile()     Save balance to disk
 * 
 * Class definition assumes user has a specific path to an account folder.
 * 
 * @author Hennessy
 * @version 1.0
 */

public class Account
{
    private int balance;
    private final String path;
    private final String name;
    
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public Account(String path, String name)
    {
        if (path == null || name == null) 
        {
            String message = "Account.java: name is null.";
            new Logger("errors.txt", message);
            System.exit(0);
        }
        
        this.path = path;
        
        this.name = name;
        
        balance = readBalanceFromFile();
    }
    
    /**
     * Getter method to return user balance.
     * @return integer value corresponding to account balance
     * NOTE: Assumes balance is stored as a four byte integer.
     */
    public int getBalance()
    { return balance; }
    
    /**
     * Getter method to return account owners name.
     * @return string user name
     */
    public String getName()
    { return name; }
    
    /**
     * Method allows user balance to be incremented.
     * @param amount integer increment value
     */
    public void deposit(int amount)
    {
        assert amount > 0;
        balance += amount;
        assert balance >= 0;
    }
    
    /**
     * Method allows user to withdraw amount from balance.
     * @param amount integer decrement value
     */
    public void withdraw(int amount)
    {
        assert amount > 0;
        if  (balance >= amount)
        { 
            balance -= amount; 
        }
        else 
        { 
            balance = 0; 
        }
        assert balance >= 0;    
    }
    
    /**
     * Method to save balance to disk.
     */
    @SuppressWarnings({"ResultOfObjectAllocationIgnored", 
        "ConvertToTryWithResources"})
    public void writeBalanceToFile()
    {        
        try
        {
            // Overwrite existing file
            FileWriter fw = new FileWriter(new File(path));
            // line.separator uses the OS specific newline character sequence
            fw.write(balance + System.getProperty("line.separator"));
            fw.close();
        }
        catch (IOException ex)
        {
            String message = "Account.java: balance.txt write error. " +
                    "Balance not saved. " +
                    ex.toString();
            new Logger("errors.txt", message);
        }
    }
    
   /**************************************************************
    * Private/Helper methods
    ***************************************************************/
    
    @SuppressWarnings({"ResultOfObjectAllocationIgnored", "ConvertToTryWithResources"})
    private int readBalanceFromFile()
    {
        String line = null;
        
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            line = reader.readLine();
            reader.close();
        }
        catch (IOException ex)
        {
            String message = "Account.java: balance.txt read error. " +
                    " Balance not retrieved from disk. " +
                    ex.toString();
            new Logger("errors.txt", message);
        }
        // If balance was not retrieved set balance to 0
        return line != null ? Integer.parseInt(line) : 0;
    }
}