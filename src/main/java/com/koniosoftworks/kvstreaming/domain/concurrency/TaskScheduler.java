package com.koniosoftworks.kvstreaming.domain.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public interface TaskScheduler {
    void schedule(Runnable runnable, int period, TimeUnit unit);
    void unschedule(Runnable runnable);
    void stopAll();
}
