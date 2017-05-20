package com.koniosoftworks.kvstreaming.data.client;

import com.koniosoftworks.kvstreaming.data.core.Connection;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by max on 5/20/17.
 */
class ServerConnection extends Connection {
    ServerConnection(Socket socket, PacketSerialization packetSerialization, EncodingAlgorithm encodingAlgorithm) throws IOException {
        super(socket, packetSerialization, encodingAlgorithm);
    }
}
