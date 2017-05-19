
package chorescomplete;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class HomePanel extends JPanel
{
    private JLabel logoLabel;
    private JLabel dateLabel;
    private Box pictureBox;
    
    @SuppressWarnings({"CallToPrintStackTrace",
        "ResultOfObjectAllocationIgnored"})
    public HomePanel(int width, int height)
    {    
        assert (width > 200) && (height > 200);
        
        logoLabel = getLogoLabel();
        
        dateLabel = getDateLabel();
        
        // Set up picture box

        // Add components to HomePanel instance
        addToPanel();
    }
    
   /**************************************************************
    * Private/Helper methods
    ***************************************************************/
    
    private JLabel getLogoLabel()
    {
        JLabel logo = new JLabel();
        logo.setIcon(getImageIcon("src/images/ChoresCompleteBanner.png"));
        return logo;
    }
    
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private ImageIcon getImageIcon(String path)
    {
        assert path != null;
        try
        {
            BufferedImage img = ImageIO.read(new File(path));
            return new ImageIcon(img);
        }
        catch (Exception ex)
        {
            String message = "HomePanel.java: could not load image = " +
                    path + " " + ex.toString();
            new Logger("errors.txt", message);
        } 
        return null;
    }
    
    private JLabel getDateLabel()
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MM/dd/yyyy");
        JLabel dLabel= new JLabel();
        dLabel.setText(dateFormat.format(date));
        dLabel.setFont(new Font("Courier", Font.PLAIN, 35));
        dLabel.setForeground(getBackgroundColor());
        return dLabel;
    }
    
    /**
     * Sets panel background color. Method used primarily to make constructor
     * code cleaner and more readable.
     */
    private Color getBackgroundColor()
    {
        // Set RGB for custom Color object
        int red   = 187;
        int green = 187;
        int blue  = 237;
        return new Color(red, green, blue);
    }
    
    private void setUpPictureBox()
    {
        // add at later point
    }
    
            
    // needs a close button on bottom which closes just the particular instance
    // of the background panel, saves state before closing.
        /* Code for constructor to load random image
        try
        {
            // Add image to background at some other point
            file = new File("images/dogs");
            assert file.canRead();
            File files[] = file.listFiles();
            SIZE = files.length;
            assert SIZE != 0;
            // E.g., random num = 4, then dog4.jpg is loaded
            int randNum = getRandomNumber();
            assert randNum < SIZE && randNum > 0;
            String path = "images/dog" + randNum + ".jpg";
            System.out.println("images/dog4.jpg");
            assert path != null;
            
        }
        catch (Exception ex)
        {
            String message = "BackgroundPanel.java: file read error "
                    + ex.toString();
            new Logger("errors.txt", message);
        }
    */
    
    /**
     * Simple random number generator to support object construction.
     * NOTE: The method is final because it is called from a constructor.
     * This guarantees this method cannot be overrode ensuring the right
     * method is called by the constructor.
     * @return random integer between 0 and SIZE.
     */
    /*
    private int getRandomNumber()
    { return (int) (Math.random() * SIZE); }
    */
    
    private void addToPanel()
    {              
        this.setBackground(Color.white);
        this.add(logoLabel);
        // Find way to put date on bottom
        this.add(dateLabel);
        //add ian and rileys money
    }
}
