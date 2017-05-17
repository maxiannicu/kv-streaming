package com.koniosoftworks.kvstreaming.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by lschidu on 5/17/17.
 */
public class ScreenShotMaker {

    private static ScreenShotMaker INSTANCE = new ScreenShotMaker();
    private Robot robot;
    private BufferedImage bufferedImage;


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

    private void doResize(int width, int height) {
        Image tmp = bufferedImage.getScaledInstance(width, height, bufferedImage.getType());
        bufferedImage = new BufferedImage(width, height, bufferedImage.getType());

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
    }


    public ScreenShotMaker makeScreenshot() {
        bufferedImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        return this;
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }


}
