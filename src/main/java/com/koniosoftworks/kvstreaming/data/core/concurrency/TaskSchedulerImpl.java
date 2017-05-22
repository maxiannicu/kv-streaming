package com.koniosoftworks.kvstreaming.data.core.concurrency;

import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class TaskSchedulerImpl implements TaskScheduler {
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private Map<Runnable,ScheduledFuture> runnableScheduledFutureMap = new HashMap<>();
    private Map<Runnable,Thread> threads = new HashMap<>();

    @Override
    public void run(Runnable runnable) {
        Thread thread = new Thread(runnable);
        threads.put(runnable,thread);
        thread.start();
    }

    @Override
    public void schedule(Runnable runnable,int period, TimeUnit unit) {
        ScheduledFuture<?> scheduledFuture = executorService.scheduleAtFixedRate(runnable, 0, period, unit);
        runnableScheduledFutureMap.put(runnable,scheduledFuture);
    }

    @Override
    public void unschedule(Runnable runnable) {
        if(runnableScheduledFutureMap.containsKey(runnable)){
            runnableScheduledFutureMap.get(runnable).cancel(true);
            runnableScheduledFutureMap.remove(runnable);
        }
        if(threads.containsKey(runnable)){
            Thread thread = threads.get(runnable);
            thread.stop();
            threads.remove(runnable);
        }
    }

    @Override
    public void stopAll() {
        runnableScheduledFutureMap.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .forEach(scheduledFuture -> scheduledFuture.cancel(true));
        runnableScheduledFutureMap.clear();
    }
}
