package com.koniosoftworks.kvstreaming.utils;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Maxian Nicu on 5/20/2017.
 */
public class Formatting {
    public static String getConnectionInfo(Socket socket){
        return String.format("%s:%s",socket.getInetAddress(),socket.getPort());
    }

    public static String getConnectionInfo(ServerSocket socket){
        return String.format("%s:%s",socket.getInetAddress(),socket.getLocalPort());
    }
}
