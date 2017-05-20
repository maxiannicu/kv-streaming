package com.koniosoftworks.kvstreaming.presentation.di.scope;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;


/**
 * Created by max on 5/20/17.
 */

public class GuiceObjectScope implements Scope {

    // Make this a ThreadLocal for multithreading.
    private ObjectScope current = null;

    @Override
    public <T> Provider<T> scope(Key<T> key, com.google.inject.Provider<T> unscoped) {
        return () -> {

            // Lookup instance
            T instance = current.get(key);
            if (instance==null) {

                // Create instance
                instance = unscoped.get();
                current.set(key, instance);
            }
            return instance;

        };
    }

    public void enter(ObjectScope scope) {
        current = scope;
    }

    public void leave() {
        current = null;
    }
}

