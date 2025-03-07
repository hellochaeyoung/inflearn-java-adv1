package thread.start;

import util.MyLogger;

import static util.MyLogger.log;

public class InnerRunnableMainV1 {

    public static void main(String[] args) {
        log("main() start");

        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable).start();

        log("main() end");
    }

    // 정적 중첩 클래스
    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            log("run()");
        }
    }
}
