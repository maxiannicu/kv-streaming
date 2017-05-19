package com.koniosoftworks.kvstreaming.presentation.di;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.koniosoftworks.kvstreaming.data.concurrency.TaskSchedulerImpl;
import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;

/**
 * Created by nicu on 5/16/17.
 */
public abstract class BaseModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(TaskScheduler.class).to(TaskSchedulerImpl.class).in(Scopes.SINGLETON);
    }
}
