package com.koniosoftworks.kvstreaming.data.video;

import com.koniosoftworks.kvstreaming.domain.props.ServerProperties;
import com.koniosoftworks.kvstreaming.domain.video.RealTimeStreamingAlgorithm;
import com.koniosoftworks.kvstreaming.utils.ScreenShotMaker;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Maxian Nicu on 5/21/2017.
 */
public class ScreenRecordAlgorithm implements RealTimeStreamingAlgorithm {
    private final Logger logger;

    @Inject
    public ScreenRecordAlgorithm(Logger logger) {
        this.logger = logger;
    }

    @Override
    public byte[] getCurrentImage() throws IOException {
        BufferedImage bufferedImage = ScreenShotMaker.getINSTANCE()
                .makeScreenshot()
                .resizeToMax(ServerProperties.BROADCAST_IMAGE_WIDTH, ServerProperties.BROADCAST_IMAGE_HEIGHT)
                .getBufferedImage();
        ByteOutputStream output = new ByteOutputStream();
        ImageIO.write(bufferedImage,"jpg", output);

        return output.getBytes();
    }
}
