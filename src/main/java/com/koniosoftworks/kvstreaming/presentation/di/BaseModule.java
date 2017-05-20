package com.koniosoftworks.kvstreaming.presentation.di;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.multibindings.MapBinder;
import com.koniosoftworks.kvstreaming.data.concurrency.TaskSchedulerImpl;
import com.koniosoftworks.kvstreaming.data.core.MessageBusImpl;
import com.koniosoftworks.kvstreaming.data.io.Base64Encoding;
import com.koniosoftworks.kvstreaming.data.io.PacketSerializationImpl;
import com.koniosoftworks.kvstreaming.data.io.algorithms.ChatMessageRequestSerializationAlgorithm;
import com.koniosoftworks.kvstreaming.data.io.algorithms.ChatMessageSerializationAlgorithm;
import com.koniosoftworks.kvstreaming.data.io.algorithms.InitializationMessageSerializationAlgorithm;
import com.koniosoftworks.kvstreaming.data.io.algorithms.PacketSerializationAlgorithm;
import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;
import com.koniosoftworks.kvstreaming.domain.core.MessageBus;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessageRequest;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.io.SerializationAlgorithm;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Created by nicu on 5/16/17.
 */
public abstract class BaseModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(TaskScheduler.class).to(TaskSchedulerImpl.class).in(Scopes.SINGLETON);
        binder.bind(PacketSerialization.class).to(PacketSerializationImpl.class).in(Scopes.SINGLETON);
        binder.bind(EncodingAlgorithm.class).to(Base64Encoding.class).in(Scopes.SINGLETON);
        binder.bind(MessageBus.class).to(MessageBusImpl.class).in(Scopes.SINGLETON);

        bindSerializationAlgorithms(binder);
    }

    private void bindSerializationAlgorithms(Binder binder) {
        MapBinder<Class,SerializationAlgorithm> mapBinder = MapBinder.newMapBinder(binder, Class.class, SerializationAlgorithm.class);
        mapBinder.addBinding(Packet.class).to(PacketSerializationAlgorithm.class);
        mapBinder.addBinding(InitializationMessage.class).to(InitializationMessageSerializationAlgorithm.class);
        mapBinder.addBinding(ChatMessage.class).to(ChatMessageSerializationAlgorithm.class);
        mapBinder.addBinding(ChatMessageRequest.class).to(ChatMessageRequestSerializationAlgorithm.class);
    }

    @Provides
    Logger provideLogger() {
        return LogManager.getLogger();
    }
}
