package com.koniosoftworks.kvstreaming.domain.dto;

import java.lang.reflect.Constructor;

import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;

/**
 * Created by nicu on 5/16/17.
 */
public enum PacketType {
    INITITIALIZATION(InitializationMessage.class),
    CHAT_MESSAGE(ChatMessage.class);

    private Class mappedClass;

    PacketType(Class mappedClass) {
        this.mappedClass = mappedClass;
    }

    public Class getMappedClass() {
        return mappedClass;
    }
}
