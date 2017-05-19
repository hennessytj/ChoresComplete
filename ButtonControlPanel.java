
package chorescomplete;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public final class ButtonControlPanel extends JPanel 
{
    private JButton ian;
    private JButton riley;
    private JButton home;
    private JButton close;
    private boolean ianClicked;
    private boolean rileyClicked;
    private boolean homeClicked;
    private boolean closeClicked;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ButtonControlPanel()
    {
        this.setBackground(getBackgroundColor());

        // Use GridLayout manager for equally spaced components
        int hGap = 50;
        int vGap = 50;
        // There will be 4 rows (num of buttons), 1 column
        this.setLayout(new GridLayout(4, 1, hGap, vGap));
        
        // Instantiate buttons, change default font
        home = new JButton("Home");
        home.setFont(new Font("Courier", Font.BOLD, 40));
        
        ian = new JButton(" Ian ");
        ian.setFont(new Font("Courier", Font.BOLD, 40));
        
        riley = new JButton("Riley");
        riley.setFont(new Font("Courier", Font.BOLD, 40));
        
        close = new JButton("Close");
        close.setFont(new Font("Courier", Font.BOLD, 40));
        
        // Add buttons and set up listeners
        this.add(home);
        home.addActionListener(new HomeButton());
        
        this.add(ian);
        ian.addActionListener(new IansButton());
        
        this.add(riley);
        riley.addActionListener(new RileysButton());
        
        this.add(close);
        close.addActionListener(new CloseButton());
    }
    
    /**
     * Creates custom color object and returns it.
     * @return custom color object
     */
    private Color getBackgroundColor()
    {
        int red   = 100;
        int green = 100;
        int blue  = 150;
        return new Color(red, green, blue);
    }
    
    public boolean isHomeClicked()
    { return homeClicked; }
    
    public boolean hasIanClicked()
    { return ianClicked; }
    
    public boolean hasRileyClicked()
    { return rileyClicked; }
    
    public boolean isCloseClicked()
    { return closeClicked; }
        
    public void setHomeClicked(boolean b)
    { homeClicked = b; }
    
    public void setIanClicked(boolean b)
    { ianClicked = b; }
    
    public void setRileyClicked(boolean b)
    { rileyClicked = b; }
    
    public void setCloseClicked(boolean b)
    { closeClicked = b; }
    
   /**************************************************************
    * Private/Helper methods
    ***************************************************************/
        
    private class HomeButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            // Signal Home clicked to ChoresComplete.java
            setHomeClicked(true);
        }
    }
    
    private class IansButton implements ActionListener
    {
        @Override
        @SuppressWarnings("ResultOfObjectAllocationIgnored")
        public void actionPerformed(ActionEvent evt)
        {
            // Signal Ian clicked to ChoresComplete.java
            setIanClicked(true);
        }
    }
    
    private class RileysButton implements ActionListener
    {
        @Override
        @SuppressWarnings("ResultOfObjectAllocationIgnored")
        public void actionPerformed(ActionEvent evt)
        {
            // Signal Riley clicked to ChoresComplete.java
            setRileyClicked(true);
        }
    }
    
    private class CloseButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ect)
        {
            // Signal Close clicked to ChoresComplete.java
            setCloseClicked(true);
        }
    }
}
