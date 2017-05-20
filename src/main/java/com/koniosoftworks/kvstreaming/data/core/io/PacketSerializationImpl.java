package com.koniosoftworks.kvstreaming.data.core.io;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.exception.SerializeException;
import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;
import com.koniosoftworks.kvstreaming.domain.io.StreamReader;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.io.SerializationAlgorithm;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.sun.xml.internal.ws.encoding.soap.DeserializationException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class PacketSerializationImpl implements PacketSerialization {
    private Map<Class,SerializationAlgorithm> algorithmMap;

    @Inject
    public PacketSerializationImpl(Map<Class, SerializationAlgorithm> algorithmMap) {
        this.algorithmMap = algorithmMap;
    }

    @Override
    public byte[] serialize(Packet packet) throws IOException {
        Object data = packet.getData();
        if(data == null)
            throw new SerializeException("Packet cannot have null data");

        ByteOutputStream byteOutputStream = new ByteOutputStream();
        SimpleStreamWriter streamSerializer = new SimpleStreamWriter(byteOutputStream);

        serialize(packet, streamSerializer);
        serialize(data, streamSerializer);

        byteOutputStream.close();

        return Arrays.copyOfRange(byteOutputStream.getBytes(),0,byteOutputStream.getCount());
    }
    @SuppressWarnings("unchecked")
    @Override
    public Packet unserialize(byte[] bytes) throws IOException {
        ByteInputStream byteInputStream = new ByteInputStream(bytes,bytes.length);
        ScannerStreamReader scannerStreamReader = new ScannerStreamReader(byteInputStream);

        Packet packet = new Packet();
        unserialize(packet, scannerStreamReader);
        Class mappedClass = packet.getPacketType().getMappedClass();
        try {
            Object data = mappedClass.getConstructor().newInstance();
            packet.setData(data);
            unserialize(data, scannerStreamReader);
        } catch (Exception e) {
            throw new UnserializeException("Unable to unserialize data object",e);
        }

        byteInputStream.close();

        return packet;
    }

    @SuppressWarnings("unchecked")
    private void serialize(Object data, SimpleStreamWriter streamSerializer) throws SerializeException {
        Class<?> dataClass = data.getClass();
        if(!algorithmMap.containsKey(dataClass))
            throw new SerializeException("Not found serialization algorithm for class "+ dataClass.getName());

        SerializationAlgorithm serializationAlgorithm = algorithmMap.get(dataClass);
        serializationAlgorithm.serialize(streamSerializer, data);
    }

    @SuppressWarnings("unchecked")
    private void unserialize(Object data, StreamReader streamReader) throws DeserializationException {
        Class<?> dataClass = data.getClass();
        if(!algorithmMap.containsKey(dataClass))
            throw new DeserializationException("Not found serialization algorithm for class "+ dataClass.getName());

        SerializationAlgorithm serializationAlgorithm = algorithmMap.get(dataClass);
        serializationAlgorithm.deserialize(streamReader, data);
    }
}
