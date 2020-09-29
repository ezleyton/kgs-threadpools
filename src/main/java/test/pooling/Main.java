package test.pooling;

import java.util.Scanner;
import java.util.concurrent.*;

/**
 * EXCUSE ME FOR THE CODE QUALITY
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("starting main");

        ExecutorService ex = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
        Type currentType = Type.CACHED;

        Boolean keepRunning = Boolean.TRUE;

        Scanner scanner = new Scanner(System.in);
        System.out.printf("Starting with cached thread pool\n");
        System.out.print("type help for help ");

        while (keepRunning) {

            System.out.print("> ");
            String command = scanner.nextLine();
            command = command.trim();
            if ("help".equals(command)) {
                System.out.println("add <number>\tadds an x amount of runnable objects to the thread pool)");
                System.out.println("schedule <number>\tschedules an x amount of runnable objects to the thread pool)");
                System.out.println("program <number>\tschedules an x amount of runnable objects to the thread pool)");
                System.out.println("info\t\t\tshow current executor pools status");
                System.out.println("set cached\t\tset thread pool to cached");
                System.out.println("set single\t\tset thread pool to single threaded");
                System.out.println("set fixed\t\tset thread pool to fixed (2 threads)");
                System.out.println("set scheduled\tset thread pool to scheduled (2 threads)");
                System.out.println("status\t\t\tshows current executor info");
                System.out.println("quit or exit\tquits or exits...");

            } else if (command.startsWith("add")) {

                String[] subcommands = command.split(" ");

                if (subcommands.length > 1) {
                    try {
                        Integer number = Integer.valueOf(subcommands[1]);
                        for (int i = 0; i < number; i++) {
                            ex.submit(new PrintRunnable());
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command");
                    }
                }

                //ex.submit(new PrintRunnable());
            } else if (command.startsWith("schedule") || command.startsWith("program")) {

                if (ex instanceof ScheduledThreadPoolExecutor ) {
                    String[] subcommands = command.split(" ");

                    if (subcommands.length > 1) {
                        try {
                            Integer number = Integer.valueOf(subcommands[1]);
                            for (int i = 0; i < number; i++) {
                                if (command.startsWith("schedule")) {
                                    ((ScheduledThreadPoolExecutor) ex).schedule(new PrintRunnable(), 5, TimeUnit.SECONDS);
                                } else {
                                    ((ScheduledThreadPoolExecutor) ex).scheduleAtFixedRate(new PrintRunnable(), 5, 12, TimeUnit.SECONDS);
                                }

                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid command");
                        }
                    }
                } else {
                    System.out.println("Executor is not scheduling compatible, please use add command");
                }


                //ex.submit(new PrintRunnable());
            }
            else if ("info".equals(command)) {
                System.out.println("Type: " + currentType);
                System.out.println("GetClass: " + ex.getClass());
                if (ex instanceof ThreadPoolExecutor) {
                    System.out.println("Executor thread pool task count: " + ((ThreadPoolExecutor) ex).getTaskCount());
                    System.out.println("Executor thread pool active count: " + ((ThreadPoolExecutor) ex).getActiveCount());
                } else if (currentType.equals(Type.SINGLE)){
                    System.out.println("Executor thread pool task count: " + "You're using a single threaded executor");
                }

            } else if("set cached".equals(command)) {
                ex = Executors.newCachedThreadPool();
                currentType = Type.CACHED;
            } else if ("set single".equals(command)) {
                ex = Executors.newSingleThreadExecutor();
                currentType = Type.SINGLE;
            } else if ("set fixed".equals(command)) {
                ex = Executors.newFixedThreadPool(2);
                currentType = Type.FIXED;
            } else if ("set scheduled".equals(command)) {
                ex = Executors.newScheduledThreadPool(2);
                currentType = Type.SCHEDULED;
            }
            else if ("exit".equals(command) || "quit".equals(command)) {
                System.out.println("bye :)");
                keepRunning = Boolean.FALSE;
            } else if ("terminate".equals(command)) {
                ex.shutdownNow();
            } else if ("status".equals(command)) {
                System.out.println("Executor shutdown: " + ex.isShutdown());
                System.out.println("Executor terminated: " + ex.isTerminated());
            }
            else if (command.isEmpty()) {

            }
            else {
                System.out.println("invalid command");
            }


        }

        ex.shutdownNow();

    }

    public enum Type {
        FIXED,
        SINGLE,
        CACHED,
        SCHEDULED
    }

}
