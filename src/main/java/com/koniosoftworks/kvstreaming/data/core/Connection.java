package com.koniosoftworks.kvstreaming.data.core;

import com.koniosoftworks.kvstreaming.data.core.io.ScannerStreamReader;
import com.koniosoftworks.kvstreaming.data.core.io.SimpleStreamWriter;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.io.StreamReader;
import com.koniosoftworks.kvstreaming.domain.io.StreamWriter;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by max on 5/20/17.
 */
public abstract class Connection {
    protected final Socket socket;
    private final PacketSerialization packetSerialization;
    private final EncodingAlgorithm encodingAlgorithm;
    private final StreamReader streamReader;
    private final StreamWriter streamWriter;
    protected final Logger logger;

    protected boolean isAlive;

    public Connection(Socket socket, PacketSerialization packetSerialization,
                      EncodingAlgorithm encodingAlgorithm, Logger logger) throws IOException {
        this.socket = socket;
        this.packetSerialization = packetSerialization;
        this.encodingAlgorithm = encodingAlgorithm;
        this.streamWriter =  new SimpleStreamWriter(socket.getOutputStream());
        this.streamReader = new ScannerStreamReader(socket.getInputStream());
        this.logger = logger;
        this.isAlive = false;
    }

    public void send(Packet packet) throws IOException {
        byte[] bytes = encodingAlgorithm.encode(packetSerialization.serialize(packet));
        logger.debug("Sending packet "+new String(bytes));
        streamWriter.put(new String(bytes));
        socket.getOutputStream().flush();
    }

    public boolean hasReceivedPacket(){
        return streamReader.hasNextString();
    }

    public Packet getPacket() throws UnserializeException {
        if(!hasReceivedPacket())
            throw new UnserializeException("No message to deserialize");

        byte[] encodedBytes = streamReader.nextString().getBytes();
        logger.debug("Received packet "+new String(encodedBytes));
        byte[] bytes = encodingAlgorithm.decode(encodedBytes);
        try {
            return packetSerialization.unserialize(bytes);
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException("Failed to read packet");
        }
    }

    public void close(){
        if(isAlive){
            try {
                socket.close();
                isAlive = false;
            } catch (IOException e) {
                logger.debug(e);
            }
        }
    }
}
