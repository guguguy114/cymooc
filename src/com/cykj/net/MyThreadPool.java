package com.cykj.net;

import com.cykj.util.ServerConsoleUtils;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description:
 * 线程池
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/17 16:06
 */
public class MyThreadPool {
    private final int coreSize = 20; // 核心线程数
    private final LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(); // 任务队列

    public MyThreadPool() {
        // 循环创建线程，然后让线程进入等待状态
        for (int i = 0; i < coreSize; i++) {
            new Thread(() -> {
                while (true) {
                    // 让线程进入等待状态 -- 基于某个事物进行等
                    MyTask task = null;
                    synchronized (workQueue) {
                        try {
                            String msg = Thread.currentThread().getName() + " waiting.";
                            ServerConsoleUtils.printOut(msg);
                            if (workQueue.isEmpty()) {
                                workQueue.wait(); // 等待中 --- 阻塞
                                String msgO = Thread.currentThread().getName() + " out.";
                                ServerConsoleUtils.printOut(msgO);
                                // 取任务干活
                            }
                            task = (MyTask) workQueue.poll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(task != null) {
                        task.start();
                    }
                }
            }).start(); // 窗口开好了
        }
    }

    /**
     * Description: 执行线程函数
     * @param task 需要执行的task线程对象
     * @author Guguguy
     * @since 2024/1/31 19:15
     */
    public void execute(MyTask task) {
        synchronized (workQueue) {
            workQueue.add(task); // 将任务添加到任务队列
            workQueue.notify(); // 通知线程干活
        }

    }
}
