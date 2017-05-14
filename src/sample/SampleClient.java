package sample;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by lschidu on 5/13/17.
 */
public class SampleClient extends Thread {

    private BufferedImage bimg;
    private Socket client;
    private Robot screenShotMaker;
    private static final String serverName = "localhost";


    @Override
    public void run() {
        int port = 6066;
        try {

            client = new Socket(serverName, port);
            screenShotMaker = new Robot();

            while (!client.isClosed()) {
                bimg = screenShotMaker.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ImageIO.write(bimg, "JPG", client.getOutputStream());

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.getOutputStream().flush();
            }

            client.close();
        } catch(IOException | AWTException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {
        SampleClient client = new SampleClient();
        client.start();
    }
}
