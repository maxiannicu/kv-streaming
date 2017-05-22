package com.koniosoftworks.kvstreaming.domain.video;

import java.io.IOException;

/**
 * Created by Maxian Nicu on 5/21/2017.
 */
public interface RealTimeStreamingAlgorithm {
    byte[] getCurrentImage() throws IOException;
}
