package net.akcegaraz.example;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class MailGenerator implements Runnable {
    private static boolean STOP;
    private BlockingQueue<Mail> mailQueue;

    public MailGenerator(BlockingQueue<Mail> mailQueue) {
        this.mailQueue = mailQueue;
    }

    @Override
    public void run() {
        System.out.println("STARTED A NEW MAIL_GENERATOR THREAD");
        while (!STOP) {
            Mail mail = new Mail("(Welcome) Mail at " + new Date());
            try {
                mailQueue.put(mail);
            } catch (InterruptedException e) {
            }
            System.out.println("GENERATED: " + mail);
        }
        System.out.println("FINISHED MAIL_GENERATOR THREAD");
    }

    public static void stopGenerating() {
        STOP = true;
    }
}