
package chorescomplete;

/**
 * Represents a single chore.  Every chore has a name, value, and completion
 * status.
 * 
 * @author Hennessy
 * @version 1.0
 */
public class Chore
{
    private final String choreName;
    private final int value;
    private boolean bComplete;
    
    public Chore(String name, int val, boolean completionStatus)
    {
        assert name != null && val > 0;
        choreName = name;
        value = val;
        bComplete = completionStatus;
    }
    
    public String getChoreName()
    { return choreName; }
    
    public int getValue()
    { return value; }
    
    public boolean isComplete()
    { return bComplete; }
    
    public void setComplete(boolean status)
    { bComplete = status; }
     
}
