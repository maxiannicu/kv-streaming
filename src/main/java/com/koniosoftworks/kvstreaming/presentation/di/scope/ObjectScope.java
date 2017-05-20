package com.koniosoftworks.kvstreaming.presentation.di.scope;

import com.google.inject.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by max on 5/20/17.
 */

public class ObjectScope {

    private Map<Key<?>,Object> store = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T get(Key<T> key) {
        return (T)store.get(key);
    }

    public <T> void set(Key<T> key, T instance) {
        store.put(key, instance);
    }

}

