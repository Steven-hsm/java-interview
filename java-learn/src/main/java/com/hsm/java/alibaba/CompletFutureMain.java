package com.hsm.java.alibaba;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @Classname CompletFutureMain
 * @Description TODO
 * @Date 2021/7/23 16:47
 * @Created by huangsm
 */
public class CompletFutureMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new  CompletableFuture<String>();
        String s = future.get();

        future.complete("12312");

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            System.out.println("task is running");
        });
        future1.get();

        CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "this is result";
            }
        }).thenApply(result->{
            return result + "1231";
        });


    }
}
