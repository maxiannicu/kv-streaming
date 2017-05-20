package com.koniosoftworks.kvstreaming.data.core;

import com.koniosoftworks.kvstreaming.data.core.io.ScannerStreamReader;
import com.koniosoftworks.kvstreaming.data.core.io.SimpleStreamWriter;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.io.StreamReader;
import com.koniosoftworks.kvstreaming.domain.io.StreamWriter;

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

    protected boolean isAlive;

    public Connection(Socket socket, PacketSerialization packetSerialization,
                      EncodingAlgorithm encodingAlgorithm) throws IOException {
        this.socket = socket;
        this.packetSerialization = packetSerialization;
        this.encodingAlgorithm = encodingAlgorithm;
        this.streamWriter =  new SimpleStreamWriter(socket.getOutputStream());
        this.streamReader = new ScannerStreamReader(socket.getInputStream());
        this.isAlive = false;
    }

    public void send(Packet packet) throws IOException {
        byte[] bytes = encodingAlgorithm.encode(packetSerialization.serialize(packet));
        streamWriter.put(new String(bytes));
        socket.getOutputStream().flush();
    }

    public boolean hasReceivedPacket(){
        return streamReader.hasNextString();
    }

    public Packet getPacket() throws UnserializeException {
        if(!hasReceivedPacket())
            throw new UnserializeException("No message to deserialize");

        byte[] bytes = encodingAlgorithm.decode(streamReader.nextString().getBytes());
        try {
            return packetSerialization.unserialize(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read packet");
        }
    }

    public void close(){
        if(isAlive){
            try {
                socket.close();
                isAlive = false;
            } catch (IOException e) {
                e.printStackTrace();
                //TODO handle exception here.
            }
        }
    }
}
