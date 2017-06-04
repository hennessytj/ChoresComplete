
package chorescomplete;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class HomePanel extends JPanel
{
    private JLabel logoLabel;
    private JLabel dateLabel;
    private JLabel pictureLabel;
    
    @SuppressWarnings({"CallToPrintStackTrace",
        "ResultOfObjectAllocationIgnored"})
    public HomePanel(int width, int height)
    {    
        assert (width > 200) && (height > 200);
        
        logoLabel = getLogoLabel();
        
        dateLabel = getDateLabel();
        
        pictureLabel = getPictureLabel();

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
    
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private JLabel getPictureLabel()
    {
        try
        {
            // Use file to get the number of images in directory
            File file = new File("src/images/dogs");
            assert file.canRead();
            // Put all files in directory into array to ascertain number of
            // pictures in directory
            File files[] = file.listFiles();
            int size = files.length;
            assert size != 0;
            // E.g., random num = 4, then dog4.jpg is loaded
            // Pictures are labeled 0 to size - 1
            int randNum = (int) (Math.random() * size) ;
            assert randNum < size && randNum >= 0;
            // Create path to use in loading image from directory
            String path = "src/images/dogs/dog" + randNum + ".jpg";
            assert path != null;
            JLabel picture = new JLabel();
            picture.setIcon(getImageIcon(path));
            return picture;
        }
        catch (Exception ex)
        {
            String message = "HomePanel.java: file read error.  Could not"
                    + " load image. "
                    + ex.toString();
            new Logger("errors.txt", message);
        }
        return null;
    }
    
    private void addToPanel()
    {              
        this.setBackground(Color.white);
        this.add(logoLabel);
        this.add(dateLabel);
        this.add(pictureLabel);
    }
}
