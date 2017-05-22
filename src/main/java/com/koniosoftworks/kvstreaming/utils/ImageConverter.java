package com.koniosoftworks.kvstreaming.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by lschidu on 5/23/17.
 */
public class ImageConverter {

    public static Image byteArrayToImage(byte[] imageData) {
        BufferedImage image = null;
        try {
             image = ImageIO.read(new ByteArrayInputStream(imageData));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SwingFXUtils.toFXImage(image, null);
    }
}
