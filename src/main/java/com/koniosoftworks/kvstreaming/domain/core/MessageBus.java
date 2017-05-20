package com.koniosoftworks.kvstreaming.domain.core;

import com.koniosoftworks.kvstreaming.domain.dto.PacketType;

import java.util.function.Consumer;

/**
 * Created by nicu on 5/20/17.
 */
public interface MessageBus {
    void register(Object object);
    void unregister(Object object);
    void post(Object message);
}
