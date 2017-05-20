package com.koniosoftworks.kvstreaming.data.core;

import com.koniosoftworks.kvstreaming.domain.core.MessageBus;
import com.koniosoftworks.kvstreaming.domain.core.annotations.SubscribeToMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by nicu on 5/20/17.
 */
public class MessageBusImpl implements MessageBus {
    private Map<Class,Set<Subscription>> map = new HashMap<>();

    @Override
    public void register(Object object) {
        getAnnotatedMethods(object)
                .forEach(this::subscribe);
    }

    @Override
    public void unregister(Object object) {
        for (Set<Subscription> subscriptions : map.values()){
            List<Subscription> collect = subscriptions.stream()
                    .filter(subscription -> subscription.object.equals(object))
                    .collect(Collectors.toList());

            collect.forEach(subscriptions::remove);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void post(Object message) {
        Set<Subscription> subscriptions = map.computeIfAbsent(message.getClass(), computeNewSet());
        subscriptions.forEach(subscription -> subscription.consumer.accept(message));
    }

    private Consumer<Method> subscribe(Object object){
        return method -> {
            SubscribeToMessage annotation = method.getAnnotation(SubscribeToMessage.class);
            Set<Subscription> consumers = map.computeIfAbsent(annotation.messageClass(), computeNewSet());
            consumers.add(new Subscription((receivedObject) -> {
                try {
                    method.invoke(object,receivedObject);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            },object));
        };
    }

    private Function<Class, Set<Subscription>> computeNewSet() {
        return aClass -> new HashSet<Subscription>();
    }

    private Stream<Method> getAnnotatedMethods(Object object){
        Class<?> aClass = object.getClass();
        Method[] methods = aClass.getMethods();
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(SubscribeToMessage.class))
                .filter(method -> method.getParameterCount() == 1)
                .filter(method -> method.getReturnType().equals(Void.TYPE));
    }

    private static class Subscription{
        private Consumer consumer;
        private Object object;

        public Subscription(Consumer consumer, Object object) {
            this.consumer = consumer;
            this.object = object;
        }
    }
}
