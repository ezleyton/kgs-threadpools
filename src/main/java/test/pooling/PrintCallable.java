package test.pooling;

import java.util.UUID;
import java.util.concurrent.Callable;

public class PrintCallable implements Callable<String>
{

    @Override
    public String call() throws Exception {
        Thread.sleep(10000L);
        return String.format("Thread [%s] has finished", UUID.randomUUID());

    }
}
