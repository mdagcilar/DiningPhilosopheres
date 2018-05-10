package com.m3c.md;

import java.io.InputStream;

public class RunnableThreadExample implements Runnable {
    private int count = 0;

    public static void main(String[] args) {
        RunnableThreadExample runnableThreadExample = new RunnableThreadExample();
        Thread thread = new Thread(runnableThreadExample);
        thread.start();

        // waits until above thread counts to 5
        while (runnableThreadExample.count != 5) {
            try{
                Thread.sleep(250);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        System.out.println("RunnableThread starting: ");

        try {
            while (count < 5) {
                Thread.sleep(5000);
                count++;
            }
        } catch (InterruptedException e) {
            System.out.println("RunnableThread interrupted");
        }
        System.out.println("RunnableThread terminating");
    }
}
