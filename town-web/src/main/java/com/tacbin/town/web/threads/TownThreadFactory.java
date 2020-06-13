package com.tacbin.town.web.threads;

import java.util.concurrent.*;

/**
 * @Description :线程池
 * @Author : Administrator
 * @Date : 2020-06-08 21:59
 **/
public class TownThreadFactory {
    private static final ExecutorService executor;

    static {
        executor = new ThreadPoolExecutor(4, 4, 1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));
    }

    public static void execute(Runnable runnable){
        executor.submit(runnable);
    }
}
