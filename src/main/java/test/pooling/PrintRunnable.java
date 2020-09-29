package test.pooling;

import java.util.UUID;

public class PrintRunnable implements Runnable {

    @Override
    public void run() {
        UUID uuid = UUID.randomUUID();
        System.out.println(String.format("\tThread [%s] has started", uuid));
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            System.out.printf("\tThread [%s] terminated", uuid);
        }

        System.out.println(String.format("\tThread [%s] has finished", uuid));
    }
}
