package com.java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorService1 {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // using Runnable
        executorService.submit(() -> System.out.println("thread using Runnable"));

        List<Integer> list = new ArrayList<>();
        Future<List<Integer>> threadUsingCallable = executorService.submit(() -> {
            System.out.println("thread using Callable");
            Thread.sleep(10000);
            for (int i = 1; i <= 1000000; i++) {
                list.add(i);
            }
            return list;
        });

        try {

            // calling private method
            method("before future task");

            List<Integer> integers = threadUsingCallable.get(11, TimeUnit.SECONDS);
            System.out.println("get future result :" + integers.size());

            // calling private method
            method("after future task");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    private static void method(String str) {
        System.out.println("I'm other task : " + str);
    }

}
