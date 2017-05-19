
package chorescomplete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Hennessy
 * @version 1.0
 */
public class Account
{
    private int balance;
    private final String name;
    
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public Account(String name)
    {
        if (name == null) 
        {
            String message = "Account.java: name is null.";
            new Logger("errors.txt", message);
            System.exit(0);
        }
        
        this.name = name;
        
        balance = readBalanceFromFile();
    }
    
    public int getBalance()
    { return balance; }
    
    public String getName()
    { return name; }
    
    public void deposit(int depositAmount)
    {
        assert depositAmount > 0;
        balance += depositAmount;
        assert balance >= 0;
    }
    
    public void withdraw(int withdrawAmount)
    {
        assert withdrawAmount > 0;
        if  (balance >= withdrawAmount)
        { 
            balance -= withdrawAmount; 
        }
        else 
        { 
            balance = 0; 
        }
        assert balance >= 0;    
    }
    
    @SuppressWarnings({"ResultOfObjectAllocationIgnored", 
        "ConvertToTryWithResources"})
    public void writeBalanceToFile()
    {
        String path = "src/users/" + name + "/account/balance.txt";
        
        try
        {
            // Overwrite existing file
            FileWriter fw = new FileWriter(new File(path));
            fw.write(balance + System.getProperty("line.separator"));
            fw.close();
        }
        catch (IOException ex)
        {
            String message = "Account.java: balance.txt write error. " + 
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
            // example to append to top line of file
            String path = "src/users/" + name + "/account/balance.txt";
            File file = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            line = reader.readLine();
            reader.close();
        }
        catch (IOException ex)
        {
            String message = "Account.java: balance.txt read error. " + 
                    ex.toString();
            new Logger("errors.txt", message);
        }
 
        return line != null ? Integer.parseInt(line) : 0;
    }
}