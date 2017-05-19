/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chorescomplete;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Hennessy
 */
public class UserBalancePanel extends JPanel
{
    // Default for JPanel is flow layout set to grid layout
    private final Person user;
    private JLabel balanceLabel;
    //private int SIZE;        // Used for random number generation, 0 to SIZE

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public UserBalancePanel(final Person p)
    {
        if (p == null)
        {
            String message = "UserBalancePanel.java: passed null person object.";
            new Logger("errors.txt", message);
            System.exit(0);
        }
        
        user = p;
        
        setUpBackgroundColor();
        
        setUpLayoutManager();
        
        setUpBalanceLabel();
        
        addComponentsToPanel();
    }
    
   /**************************************************************
    * Private/Helper methods
    ***************************************************************/
    
    /**
     * Sets panel background color. Method used primarily to make constructor
     * code cleaner and more readable.
     */
    private void setUpBackgroundColor()
    {
        // Set RGB for custom Color object
        int red   = 244;
        int green = 246;
        int blue  = 249;
        this.setBackground(new Color(red, green, blue));
    }
    
    private void setUpLayoutManager()
    {
        int hGap = 5;
        int vGap = 5;
        this.setLayout(new GridLayout(2, 1, hGap, vGap));
    }
    
    private void setUpBalanceLabel()
    {
        assert user != null;
        balanceLabel = new JLabel();
        int balance = user.getAmountEarned();
        int dollars = balance / 100;
        int cents   = balance % 100;
        balanceLabel.setText(String.format("%s $%d.%02d ", "Earned:", dollars, cents));
        balanceLabel.setFont(new Font("Courier", Font.BOLD, 40));
        //balanceLabel.setOpaque(true);
        assert balanceLabel != null;
    }
    
    private void addComponentsToPanel()
    {
        // assert pictureBox != null;
        assert balanceLabel != null;
        // add picture here to be on top
        this.add(balanceLabel);
    }
}
