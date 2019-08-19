package net.akcegaraz.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
    //KEEP THREAD POOL SIZE MORE THEN GENERATOR COUNT + DISPATCHER COUNT
    private static final int THREAD_POOL_SIZE = 50;
    private static final int QUEUE_CAPACITY = 3;
    private static final int GENERATOR_COUNT = 20;
    private static final int DISPATCHER_COUNT = 25;
    private static final int SECONDS_TO_GENERATE_MAILS = 20;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        ArrayBlockingQueue<Mail> blockingQueue = new ArrayBlockingQueue(QUEUE_CAPACITY);
        for (int i = 0; i < GENERATOR_COUNT; i++) {
            executorService.execute(new MailGenerator(blockingQueue));
        }
        for (int i = 0; i < DISPATCHER_COUNT; i++) {
            executorService.execute(new MailDispatcher(blockingQueue));
        }

        waitForSpecifiedTimeOfMailDispatching();
        MailGenerator.stopGenerating();
        waitUntilBlockingQueueEmpty(blockingQueue);
        MailDispatcher.stopDispatching();

        executorService.shutdownNow();
    }

    private static void waitForSpecifiedTimeOfMailDispatching() {
        try {
            Thread.sleep(1000 * SECONDS_TO_GENERATE_MAILS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void waitUntilBlockingQueueEmpty(ArrayBlockingQueue<Mail> blockingQueue) {
        while (!blockingQueue.isEmpty()) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
