package com.koniosoftworks.kvstreaming.data.video;

import com.koniosoftworks.kvstreaming.domain.video.RealTimeStreamingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.video.StaticStreamingAlgorithm;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Maxian Nicu on 5/21/2017.
 */
public class ScreenRecordAlgorithm implements RealTimeStreamingAlgorithm {
    private final Robot screenShotMaker;
    private final Logger logger;

    @Inject
    public ScreenRecordAlgorithm(Logger logger) {
        this.logger = logger;
        try {
            screenShotMaker = new Robot();
        } catch (AWTException e) {
            logger.error(e);
            throw new RuntimeException("Could not start screen streaming",e);
        }
    }

    @Override
    public byte[] getCurrentImage() {
        BufferedImage screenCapture = screenShotMaker.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ByteOutputStream byteOutputStream = new ByteOutputStream();

        try {
            ImageIO.write(screenCapture,"jpg",byteOutputStream);
            return byteOutputStream.getBytes();
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
