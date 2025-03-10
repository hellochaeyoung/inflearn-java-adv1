package thread.control.yield;

import thread.start.HelloRunnable;

import static util.ThreadUtils.sleep;

public class YieldMain {
    static final int THREAD_COUNT = 1000;
    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(new MyRunnable());
            thread.start();
        }


    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);

                //sleep(1);

                Thread.yield(); // 스케줄링 대기큐로 다시 들어감, 양보하는거라서, 그래도 Runnable 상태(runnable 상태 2가지: CPU에서 처리중일 때, 스케줄링 큐에서 대기할때)
            }
        }
    }
}
