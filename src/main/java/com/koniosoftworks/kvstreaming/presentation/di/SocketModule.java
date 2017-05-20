package com.koniosoftworks.kvstreaming.presentation.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.koniosoftworks.kvstreaming.data.io.ScannerStreamReader;
import com.koniosoftworks.kvstreaming.data.io.SimpleStreamWriter;
import com.koniosoftworks.kvstreaming.data.server.ClientConnection;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.io.StreamReader;
import com.koniosoftworks.kvstreaming.domain.io.StreamWriter;
import com.koniosoftworks.kvstreaming.presentation.di.scope.GuiceObjectScope;
import com.koniosoftworks.kvstreaming.presentation.di.scope.ObjectScope;
import com.koniosoftworks.kvstreaming.presentation.di.scope.PerSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by max on 5/20/17.
 */
public class SocketModule extends AbstractModule {
    private Socket socket;

    public SocketModule(Socket socket) {
        this.socket = socket;
    }

    @Provides
    Socket getSocket(){
        return socket;
    }

    @Provides
    InputStream getInputStream(){
        try {
            return socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    OutputStream getOutputStream(){
        try {
            return socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    StreamReader getStreamReader(InputStream inputStream){
        return new ScannerStreamReader(inputStream);
    }

    @Provides
    StreamWriter getStreamWriter(OutputStream outputStream){
        return new SimpleStreamWriter(outputStream);
    }


    @Provides
    public ClientConnection getClientConnection(PacketSerialization packetSerialization,
                                                EncodingAlgorithm encodingAlgorithm,
                                                StreamReader streamReader,
                                                StreamWriter streamWriter){
        return new ClientConnection(socket, packetSerialization, encodingAlgorithm, streamReader, streamWriter);
    }

    @Override
    protected void configure() {

    }
}
