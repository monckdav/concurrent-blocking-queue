package net.akcegaraz.example;

import java.util.concurrent.BlockingQueue;

public class MailDispatcher implements Runnable {
    private static boolean STOP;
    private BlockingQueue<Mail> mailQueue;

    public MailDispatcher(BlockingQueue<Mail> mailQueue) {
        this.mailQueue = mailQueue;
    }

    @Override
    public void run() {
        System.out.println("STARTED A NEW MAIL_DISPATCHER THREAD");
        while (!STOP) {

            try {
                Mail mail = mailQueue.take();
                System.out.println("DISPATCHING: " + mail);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("STOP MAIL_DISPATCHER THREAD");
    }

    public static void stopDispatching() {
        MailDispatcher.STOP = true;
    }
}
