package cute.finalproject;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Page extends JFrame {
    private JLabel welcome;
    private ImageIcon image;
    Timer timer;
    public Page()
    {
        File f = new File("type.mp3");
        Media _media = new Media(f.toURI().toString());
        final JFXPanel fxPanel = new JFXPanel();//必須有這一行，並且要在MediaPlayer創建之前
        MediaPlayer _mediaPlayer = new MediaPlayer(_media);
        _mediaPlayer.play();

        this.setLayout(null);
        Color bkg = new Color(26,25,25);//黑色
        getContentPane().setBackground(bkg); //底色為黑

        welcome=new JLabel();
        image = new ImageIcon("page.gif");
        Image img1 = image.getImage();
        img1 = img1.getScaledInstance(900, 300, Image.SCALE_DEFAULT); //gif大小
        image.setImage(img1);
        welcome.setIcon(image);
        welcome.setBounds(0,150,1000,200); //位置

        SimpleListener l=new SimpleListener();
        timer = new Timer(2500,l); //創建一個計時器，時間間隔2500毫秒
        timer.start();  

        this.add(welcome);
        
    }
     private class SimpleListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            dispose();
            Searchpage page1 = new Searchpage();
            page1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            page1.setSize(900, 600); // set frame size
            page1.setVisible(true); // display frame 
            timer.stop();
        }
    }
    
}
