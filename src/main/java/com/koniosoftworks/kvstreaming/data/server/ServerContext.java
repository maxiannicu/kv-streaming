package com.koniosoftworks.kvstreaming.data.server;

import java.net.InetAddress;

/**
 * Created by Maxian Nicu on 5/21/2017.
 */
public class ServerContext {
    private InetAddress address;
    private int tcpPort;
    private int udpPort;

    public ServerContext(InetAddress address, int tcpPort, int udpPort) {
        this.address = address;
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public int getUdpPort() {
        return udpPort;
    }
}
