package com.koniosoftworks.kvstreaming.presentation.di;

import com.google.inject.*;
import com.google.inject.multibindings.MapBinder;
import com.koniosoftworks.kvstreaming.data.core.concurrency.TaskSchedulerImpl;
import com.koniosoftworks.kvstreaming.data.core.io.Base64Encoding;
import com.koniosoftworks.kvstreaming.data.core.io.PacketSerializationImpl;
import com.koniosoftworks.kvstreaming.data.core.io.algorithms.*;
import com.koniosoftworks.kvstreaming.data.video.ScreenRecordAlgorithm;
import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessageRequest;
import com.koniosoftworks.kvstreaming.domain.dto.messages.DisconnectMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.io.SerializationAlgorithm;
import com.koniosoftworks.kvstreaming.domain.video.RealTimeStreamingAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by nicu on 5/16/17.
 */
public abstract class BaseModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(TaskScheduler.class).to(TaskSchedulerImpl.class).in(Scopes.SINGLETON);
        binder.bind(PacketSerialization.class).to(PacketSerializationImpl.class).in(Scopes.SINGLETON);
        binder.bind(EncodingAlgorithm.class).to(Base64Encoding.class).in(Scopes.SINGLETON);
        binder.bind(RealTimeStreamingAlgorithm.class).to(ScreenRecordAlgorithm.class).in(Scopes.SINGLETON);
        bindSerializationAlgorithms(binder);
    }

    private void bindSerializationAlgorithms(Binder binder) {
        MapBinder<Class,SerializationAlgorithm> mapBinder = MapBinder.newMapBinder(binder, Class.class, SerializationAlgorithm.class);
        mapBinder.addBinding(Packet.class).to(PacketSerializationAlgorithm.class);
        mapBinder.addBinding(InitializationMessage.class).to(InitializationMessageSerializationAlgorithm.class);
        mapBinder.addBinding(ChatMessage.class).to(ChatMessageSerializationAlgorithm.class);
        mapBinder.addBinding(ChatMessageRequest.class).to(ChatMessageRequestSerializationAlgorithm.class);
        mapBinder.addBinding(DisconnectMessage.class).to(DisconnectMessageSerializationAlgorithm.class);
    }

    @Provides
    Logger provideLogger() {
        return LogManager.getLogger();
    }
}
