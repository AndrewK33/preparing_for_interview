package main.java.Lesson_3.Exercise_two;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeCounter {

    public static void main(String[] args) {
        CommonResource commonResource= new CommonResource();
        ReentrantLock locker = new ReentrantLock();
        for (int i = 1; i < 6; i++){

            Thread t = new Thread(new CountThread(commonResource, locker));
            t.setName("Thread "+ i);
            t.start();
        }
    }

    static class CommonResource{
        int x=0;
    }




    static class CountThread implements Runnable{
        CommonResource res;
        ReentrantLock locker;
        CountThread(CommonResource res, ReentrantLock lock){
            this.res=res;
            locker = lock;
        }


        public void run(){
            try{
                res.x=1;
                for (int i = 1; i < 5; i++){
                    locker.lock();
                    System.out.printf("%s %d \n", Thread.currentThread().getName(), res.x);
                    res.x++;
                    Thread.sleep(1000);
                    locker.unlock();
                }
            }
            catch(InterruptedException e){
                System.out.println(e.getMessage());
            }


        }
    }

}
