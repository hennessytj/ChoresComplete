// TODO: add swing timer to refresh for each new day
// TODO: add swing timer to gracefull shutdown after one hour
// RECALL: command + shift + i to auto imports
package chorescomplete;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChoresComplete 
{
    private JFrame frame;
    private ButtonControlPanel controlPanel; // Always on left side of frame
    
    // CardLayout panels begin
    private JPanel cards;                     // Manages cards for CardLayout
    private HomePanel homePanel;              // refered to by HOMECARD
    private BackgroundPanel ianChoresPanel;   // refered to by IANCARD
    private BackgroundPanel rileyChoresPanel; // refered to by RILEYCARD
    
    // CardLayout panels end
    private Person ian;
    private Person riley;

    // Constant Values
    private static final int FRAME_WIDTH  = 1000;
    private static final int FRAME_HEIGHT = 700;
    private static final String HOMECARD = "home";
    private static final String IANCARD = "ian";
    private static final String RILEYCARD = "riley";

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) 
    {
        new ChoresComplete().go();
    }
    
    /**
     * Launches and configures initial GUI state.
     */
    public void go()
    {        
        // Get the day of week, if Friday need to handle saving prior
        // weeks balance and reseting balance for new week
        // Check if instance exists for today
        // Probably load each Person instance
        setUpTrayIcon();
        
        controlPanel = new ButtonControlPanel();

        loadUsers();
        
        // Set up cards managed by CardLayout panel
        homePanel = new HomePanel(FRAME_WIDTH, FRAME_HEIGHT);
        ianChoresPanel = new BackgroundPanel(ian);
        rileyChoresPanel = new BackgroundPanel(riley);
    
        configureCardLayout();
        
        configureFrame();

        bringCardToFront(HOMECARD);
        
        listenForClicks();
    }
    
    @SuppressWarnings({"ResultOfObjectAllocationIgnored", 
        "ConvertToTryWithResources"})
    private void loadUsers()
    {
        ian = new Person("ian");
        riley = new Person("riley");
    }
    
    /**
     * Instantiates cards JPanel and configures it to manage
     * three cards used in the card layout.  Primarily used
     * to enhance readability by putting like code into a thoughtfully
     * named method.
     */
    private void configureCardLayout()
    {
        assert homePanel != null;
        assert ianChoresPanel != null;
        assert rileyChoresPanel != null;
        cards = new JPanel(new CardLayout());
        assert cards != null;
        cards.add(HOMECARD, homePanel);        
        cards.add(IANCARD, ianChoresPanel);
        cards.add(RILEYCARD, rileyChoresPanel);
    }
       
    /**
     * Sets up the main JFrame frame for GUI.  Primarily used
     * to enhance readability by putting like code into a thoughtfully
     * named method.
     */
    private void configureFrame()
    {
        assert cards != null;
        assert controlPanel != null;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, cards);
        frame.getContentPane().add(BorderLayout.WEST, controlPanel);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        frame.setAlwaysOnTop(true);
        frame.toFront();
        frame.setVisible(true);
    }
    
    /**
     * Takes a string name corresponding to panel being managed
     * by card layout and brings displays it.
     * @param cardName name of panel (card)
     */
    private void bringCardToFront(String cardName)
    {
        assert cards != null;
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, cardName);
    }
    
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private void setUpTrayIcon()
    {
        if (!SystemTray.isSupported())
        {
            String message = "ChoresComplete.java: SystemTray not supported. ";
            new Logger("errors.txt", message);
            return;                    
        }
        
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File("src/images/ChoresCompleteIcon.png"));
        }
        catch (Exception ex)
        {
            String message = "ChoresComplete.java: icon image load error. " +
                    ex.toString();
            new Logger("errors.txt", message);
        }
        
        if (img == null)
        {
            String message = "ChoresComplete.java: ChoresCompleteIcon.png is null. ";
            new Logger("errors.txt", message);
            return;                    
        }
        
        final TrayIcon trayIcon = new TrayIcon(img, "Chores Complete");
        final SystemTray tray = SystemTray.getSystemTray();
        
        try 
        {
            tray.add(trayIcon);
        } 
        catch (AWTException ex) 
        {
            String message = "ChoresComplete.java: could not add tray icon. " +
                    ex.toString();
            new Logger("errors.txt", message);
        }
    }
    
    @SuppressWarnings({"ResultOfObjectAllocationIgnored", "SleepWhileInLoop"})
    private void listenForClicks()
    {
        while (true)
        {
            // Each button's state is returned to false after click
            // to ensure each click is treated as a single event
            if (controlPanel.hasIanClicked()) 
            {
                bringCardToFront(IANCARD);
                // call method on ianChoresPanel (backgroundpanel) to
                // check for selected
                controlPanel.setIanClicked(false);
            }
            else if (controlPanel.hasRileyClicked())
            {
                bringCardToFront(RILEYCARD);
                // call method on ianChoresPanel (backgroundpanel) to
                // check for selected
                controlPanel.setRileyClicked(false);
            }
            else if (controlPanel.isHomeClicked())
            {
                bringCardToFront(HOMECARD);
                controlPanel.setHomeClicked(false);
            }
            else if (controlPanel.isCloseClicked())
            {
                saveAndClose();
                System.exit(0);
            }
            // Go to sleep, wake up then check again for clicks
            // Needed for the click behavior to work
            sleep();
        } 
    }
    
    /**
     * Used to gracefully shut down application.  Saves pertinent state between
     * executions.
     */
    private void saveAndClose()
    {
        assert ian != null;
        assert riley != null;
        ian.save();
        riley.save();
    }
    
    /**
     * Used to put process to sleep for a short duration.
     * @throws ex threads throw exceptions
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private void sleep()
    {           
        try
        {
            Thread.sleep(100);
        }
        catch (Exception ex)
        {
            String message = "ChoresComplete.java: thread sleep error"
                + ex.toString();
            new Logger("errors.txt", message);
        }
    }
}