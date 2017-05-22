package com.koniosoftworks.kvstreaming.domain.dto;

import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessageRequest;
import com.koniosoftworks.kvstreaming.domain.dto.messages.DisconnectMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;

/**
 * Created by nicu on 5/16/17.
 */
public enum PacketType {
    INITIALIZATION(InitializationMessage.class),
    CHAT_MESSAGE(ChatMessage.class),
    CHAT_MESSAGE_REQUEST(ChatMessageRequest.class),
    DISCONNECT(DisconnectMessage.class);

    private Class mappedClass;

    PacketType(Class mappedClass) {
        this.mappedClass = mappedClass;
    }

    public Class getMappedClass() {
        return mappedClass;
    }
}
