package sample;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;

/**
 * Created by lschidu on 5/13/17.
 */
public class SampleServer extends Thread {

    private ServerSocket serverSocket;
    private Socket server;
    private JFrame window;
    private JLabel label;
    private static final int MAX_IMAGE_SIZE = 100 * 1024 * 1024;


    public SampleServer(int port) throws Exception {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(180000);
    }

    public void run() {
        createWindow();

        try {
            server = serverSocket.accept();
            readImages(server.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void createWindow() {
        window = new JFrame("ScreenSharing");
        window.setSize(800, 600);
        label = new JLabel("Waiting for connection ...");
        window.getContentPane().add(label);
        window.setVisible(true);
    }


    private void readImages(InputStream stream) throws IOException {
        stream = new BufferedInputStream(stream);

        while (true) {
            stream.mark(MAX_IMAGE_SIZE);

            ImageInputStream imgStream =
                    ImageIO.createImageInputStream(stream);

            Iterator<ImageReader> i =
                    ImageIO.getImageReaders(imgStream);
            if (!i.hasNext()) {
                break;
            }

            ImageReader reader = i.next();
            reader.setInput(imgStream);

            BufferedImage image = reader.read(0);
            if (image == null) {
                break;
            }

            label.setIcon(new ImageIcon(image));
            long bytesRead = imgStream.getStreamPosition();

            stream.reset();
            stream.skip(bytesRead);
        }
    }

    public static void main(String [] args) throws Exception {
        Thread t = new SampleServer(6066);
        t.start();
    }
}