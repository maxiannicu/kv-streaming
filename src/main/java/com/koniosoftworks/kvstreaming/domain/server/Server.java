package com.koniosoftworks.kvstreaming.domain.server;

import com.koniosoftworks.kvstreaming.domain.video.RealTimeStreamingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.video.StaticStreamingAlgorithm;

/**
 * Created by nicu on 5/15/17.
 */
public interface Server {
    void start(int port);
    void stop();
    void startStreaming(StaticStreamingAlgorithm algorithm);
    void startStreaming(RealTimeStreamingAlgorithm algorithm);
    void stopStreaming();
}
