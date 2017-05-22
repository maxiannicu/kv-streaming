package com.koniosoftworks.kvstreaming.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * Created by lschidu on 5/17/17.
 */
public class ScreenShotMaker {

    private static ScreenShotMaker INSTANCE = new ScreenShotMaker();
    private Robot robot;
    private BufferedImage bufferedImage;
    private final static float QUALITY = 0.1f;

    private ScreenShotMaker() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static ScreenShotMaker getINSTANCE() {
        return INSTANCE;
    }

    public ScreenShotMaker resize(int width, int height) {
        doResize(width, height);
        return this;
    }

    public ScreenShotMaker resize(float scale) {
        doResize((int)(bufferedImage.getWidth() * scale), (int)(bufferedImage.getHeight() * scale));
        return this;
    }

    private void reduceQuality() {
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(QUALITY);
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();

        try {
            jpgWriter.setOutput(ImageIO.createImageOutputStream(compressed));
        } catch (IOException e) {
            e.printStackTrace();
        }
        IIOImage outputImage = new IIOImage(bufferedImage, null, null);
        try {
            jpgWriter.write(null, outputImage, jpgWriteParam);
            InputStream in = new ByteArrayInputStream(compressed.toByteArray());
            bufferedImage = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        jpgWriter.dispose();
    }

    private void doResize(int width, int height) {
        Image tmp = bufferedImage.getScaledInstance(width, height, bufferedImage.getType());
        bufferedImage = new BufferedImage(width, height, bufferedImage.getType());

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
    }


    public ScreenShotMaker makeScreenshot() {
        bufferedImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        reduceQuality();
        return this;
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }


}
