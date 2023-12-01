package clients.customer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
public class Advert extends JFrame {
    private JLabel images;
    private ArrayList<String> imagePath;
    int index;

    public static void main(String[] args) {
        new Advert().setVisible(true);
    }

    public Advert(){
        String filepath = "music/modernmusic.mp3";
        backgroundMusic(filepath);

        //array list for images
        imagePath = new ArrayList<>();
        imagePath.add("images/pic0001.jpg"); // adds image to arraylist
        imagePath.add("images/pic0002.jpg");
        imagePath.add("images/pic0003.jpg");
        imagePath.add("images/pic0004.jpg");
        imagePath.add("images/pic0005.jpg");
        imagePath.add("images/pic0006.jpg");
        imagePath.add("images/pic0007.jpg");

        index = 0; // set index to 0


        setTitle("Advertisement"); // title of window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);


        images = new JLabel(); // add images
        add(images);
        images.setBounds(100, -100, 500, 500); // image bounds


        JButton nextButton = new JButton("Next");
        JButton previousButton = new JButton("Previous"); //buttons


        nextButton.addActionListener(new ActionListener() { // event listener for next buttons
            @Override
            public void actionPerformed(ActionEvent e) {
                nextImage();
            }
        });
        previousButton.addActionListener(new ActionListener() { // event listener for previous button
            @Override
            public void actionPerformed(ActionEvent e) {
                previousImage();
            }
        });


    // add to PANEL
        JLabel text = new JLabel("SALE!");
        add(text); // sale text
        text.setFont(new Font("Ariel", Font.BOLD, 25));
        nextImage(); // add image to screen
        text.setBounds(200, 300, 100, 50); // buttons
        JPanel buttons = new JPanel();
        buttons.add(previousButton);
        buttons.add(nextButton);
        add(buttons);
        buttons.setBounds(300, 0, 100, 100);


        setResizable(false); // stops resizing of frame
    }


    private void showCurrentImage() {
        if (index >= 0 && index < imagePath.size()) {
            String path = imagePath.get(index);
            try {
                int W = 250;
                int H = 200;
                BufferedImage image = ImageIO.read(new File(path)); // draw images to screen
                BufferedImage resizedImage = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB); // new bufferedimage to allow for resizing of image
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(image, 0, 0, W, H, null); // Graphics2D to draw image to screen
                g2d.dispose();
                images.setIcon(new ImageIcon(resizedImage)); // set icon as resizedImage
            } catch (IOException e) { // catch errors
                e.printStackTrace();
            }
        }
    }

    private void nextImage() {
        index = (index + 1) % imagePath.size();
        showCurrentImage(); // cycle through images
    }
    private void previousImage() {
        index = (index - 1 + imagePath.size()) % imagePath.size();
        showCurrentImage();
    }

    public static void backgroundMusic(String filePath){

        try {
            File musicFilePath = new File(filePath);

            if (musicFilePath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFilePath);
                Clip clip = AudioSystem.getClip();
                clip.start();
            }
            else {
                System.out.println("Error: Cant find file");
            }
        } catch(Exception e ){
            System.out.println(e);
        }

    }

}
