
package chorescomplete;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
* Panel which displays a particular day's chores for the given user.
* 
* <P>Requires path containing images.  This path is essential to the
* correct functioning.  Every instantiation randomly selects an image
* in the directory and uses it as the background for the panel.
*  
* @author Timothy Hennessy
* @version 1.0
*/
public class BackgroundPanel extends JPanel
{
    private Person user;
    private UserBalancePanel usersBalancePanel;
    private ArrayList<Chore> usersChores; // Contains persons chores for the day
    private JLabel[] choreNameLabels;     // Labels for each chore
    private JCheckBox[] checkBoxes;       // Check boxes for chores
    private final int numberOfChores;     // Total number of chores
        
    /** Only constructor for this class
     * @param p
     */
    @SuppressWarnings({"ResultOfObjectAllocationIgnored", 
        "OverridableMethodCallInConstructor", 
        "CallToThreadStartDuringObjectConstruction"})
    public BackgroundPanel(Person p)
    {
        assert p != null;
        
        user = p;
        
        usersChores = user.getChoreList();
        
        numberOfChores = usersChores.size();
        
        this.setLayout(new BorderLayout());
                
        this.setBackground(getBackgroundColor());
        
        this.add(BorderLayout.NORTH, getNameLabel());
        
        this.add(BorderLayout.WEST, getChoreLabels());

        this.add(BorderLayout.CENTER, getCheckBoxes());
        
        this.add(BorderLayout.EAST, setUpBalancePanel());
        
        runConcurrently();
    }
    
    /**
     * Sets panel background color. Method used primarily to make constructor
     * code cleaner and more readable.
     */
    private Color getBackgroundColor()
    {
        // Set RGB for custom Color object
        int red   = 244;
        int green = 246;
        int blue  = 249;
        return new Color(red, green, blue);
    }
    
    /**
     * Sets up name in the panel.  Method used primarily to
     * make constructor code cleaner and more readable.
     */
    private JLabel getNameLabel()
    {
        // Set persons name up
        JLabel nameLabel = new JLabel();
        nameLabel.setText("    " + capitalizeName(user.getName()) + "'s Chores");
        nameLabel.setFont(new Font("Courier", Font.BOLD, 64));
        //nameLabel.setForeground(Color.white);
        return nameLabel;
    }
    
    /**
     * Builds a JPanel with all chore names.
     * @return JPanel with all chore names on it as JLabels
     */
    private JPanel getChoreLabels()
    {
        buildChoreNameLabels();
        JPanel choreNameLabelPanel = new JPanel();
        choreNameLabelPanel.setBackground(getBackgroundColor());
        int hGap = 5;
        int vGap = 5;
        choreNameLabelPanel.setLayout(
                new GridLayout(numberOfChores, 1, hGap, vGap));
        for (int i = 0; i < numberOfChores; i++)
            choreNameLabelPanel.add(choreNameLabels[i]);
        return choreNameLabelPanel;
    }
    
    /**
     * Builds and returns JPanel with check boxes.  The number of
     * check boxes is equivalent to the number of chores.
     * @return JPanel set up with check boxes
     */
    private JPanel getCheckBoxes()
    {
        checkBoxes = new JCheckBox[numberOfChores];
        JPanel checkBoxPanel = new JPanel();
        int hGap = 5;
        int vGap = 5;
        checkBoxPanel.setBackground(getBackgroundColor());
        checkBoxPanel.setLayout(new GridLayout(numberOfChores, 1, hGap, vGap));
        for (int i = 0; i < numberOfChores; i++) 
        {   
            checkBoxes[i] = new JCheckBox();
            boolean choreCompletionStatus = usersChores.get(i).isComplete();
            checkBoxes[i].setSelected(choreCompletionStatus);
            // add each check box to JPanel
            checkBoxPanel.add(checkBoxes[i]);
        }
        return checkBoxPanel;
    }
    
    /**
     * Instantiates a new UserBalancePanel.
     * @return 
     */
    private UserBalancePanel setUpBalancePanel()
    {
        usersBalancePanel = new UserBalancePanel(user);
        return usersBalancePanel;
    }
    
    /**
     * Uses an ArrayList of chores to fill an array of JLabel's.
     * @param sz number of names
     * @return array of name JLabel's 
     */
    private void buildChoreNameLabels()
    {
        assert numberOfChores >= 0;
        // Put each chore name into string array
        String[] choreNames = new String[numberOfChores];
        // Use i as index for string array since using a for each loop
        // to iterate over ArrayList
        int i = 0;
        // Fill string array with the names of the chores
        for (Chore c : usersChores)
            choreNames[i++] = c.getChoreName();

        // Build array of JLabels and put each chore name as text
        choreNameLabels = new JLabel[numberOfChores];
        // Reset i to 0 and use for index again
        for (i = 0; i < numberOfChores; i ++)
        {
            choreNameLabels[i] = new JLabel();
            choreNameLabels[i].setHorizontalAlignment(SwingConstants.RIGHT);
            choreNameLabels[i].setText(choreNames[i]);
            choreNameLabels[i].setFont(new Font("Courier", Font.BOLD, 40));
            choreNameLabels[i].setForeground(new Color(187, 187, 237));
            //choreNameLabels[i].setOpaque(true);
            //choreNameLabels[i].setForeground(Color.white);
        }
    }
    
    /**
     * Capitalizes only the first letter of string passed in.
     * @param name string to capitalize
     * @return another string identical to name, but with first
     * letter capitalized
     */
    private String capitalizeName(final String name)
    {   // Get first character capitalize it then append the second letter on
        // from name to that character, 
        // E.g., name = robin -> R + obin = Robin
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
    
    /**
     * Runs concurrently to main thread execution allowing for user to check
     * and un-check boxes to signal chore completion.
     */
    private void concurrentCheckBoxLoop()
    {
        while (true)
        {
            for (int i = 0; i < numberOfChores; i++)
            {
                // User checked box to signal chore completion.
                // If chore has not already been marked completed,
                // mark as completed and increment balance.
                // Essentially, this case distinguishes from the cases when
                // the box is checked and the chore has been marked complete
                // already- i.e., avoids paying them for a previously 
                // completed chore.
                boolean checkBoxIsChecked = checkBoxes[i].isSelected();
                boolean choreMarkedCompleted = usersChores.get(i).isComplete();

                if (checkBoxIsChecked && !choreMarkedCompleted) 
                {
                    // Mark chore complete
                    usersChores.get(i).setComplete(true);
                    // Get the value of the chore and add it to users balance
                    user.incAmountEarned(usersChores.get(i).getValue());
                    this.repaint();
                    //System.out.println(usersChores.get(i).getValue());
                }
                else if (!checkBoxIsChecked && choreMarkedCompleted)
                {
                    // This is the case where user decides to uncheck a box
                    // Set chore back to not complete
                    usersChores.get(i).setComplete(false);
                    user.decAmountEarned(usersChores.get(i).getValue());
                    this.repaint();
                    //System.out.println("Oops, new balance is " + getBalance());
                }
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        this.revalidate();
        this.add(BorderLayout.EAST, setUpBalancePanel());
    }
    
    /**
     * Sets up thread to enable a portion of the BackgroundPanel
     * to run concurrently.
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private void runConcurrently()
    {
        try
        {
            Runnable concurrentLoop = new RunnableLoop();
            Thread job = new Thread(concurrentLoop);
            job.start();
        }
        catch (Exception ex)
        {
            String message = "BackgroundPanel.java: concurrency issue. " +
                    ex.toString();
            new Logger("errors.txt", message);
        }
    }
    
    private class RunnableLoop implements Runnable
    {
        @Override
        public void run()
        {
            concurrentCheckBoxLoop();
        }
    }
}