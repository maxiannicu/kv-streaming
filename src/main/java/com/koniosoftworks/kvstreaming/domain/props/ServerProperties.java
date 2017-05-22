package com.koniosoftworks.kvstreaming.domain.props;

import java.net.InetAddress;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class ServerProperties {
    public static final int MAX_CONNECTIONS_ALLOWED = 10;
    public static final int VIDEO_STREAMING_INTERVAL_SENDING = 80; //ms
    public static final String BROADCAST_LISTENING = "0.0.0.0";
    public static final String BROADCAST_SENDING = "255.255.255.255";
    public static final int BROADCAST_PORT = 31013;
    public static final int BROADCAST_IMAGE_WIDTH = 560;
    public static final int BROADCAST_IMAGE_HEIGHT = 320;
}
