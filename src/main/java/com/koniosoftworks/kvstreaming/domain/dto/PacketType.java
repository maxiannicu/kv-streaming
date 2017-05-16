package com.koniosoftworks.kvstreaming.domain.dto;

import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by nicu on 5/16/17.
 */
public enum PacketType {
    INITITIALIZATION(InitializationMessage.class),
    CHAT_MESSAGE(ChatMessage.class);

    private Class<? extends NetworkSerializable> model;

    PacketType(Class model) {
        this.model = model;
    }

    public NetworkSerializable initializeModel() throws UnserializeException {
        try {
            Constructor constructor = model.getConstructor();
            return (NetworkSerializable) constructor.newInstance();
        } catch (Exception e) {
            throw new UnserializeException("Unnable to create model for "+this.toString(),e);
        }
    }
}
