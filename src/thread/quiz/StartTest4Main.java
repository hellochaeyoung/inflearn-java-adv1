package thread.quiz;

import util.MyLogger;

import static java.lang.Thread.sleep;
import static util.MyLogger.log;

public class StartTest4Main {
    public static void main(String[] args) {

        Thread threadA = new Thread(new PrintRunnable("A", 1000L));
        threadA.setName("Thread-A");

        Thread threadB = new Thread(new PrintRunnable("B", 500L));
        threadB.setName("Thread-B");

        threadA.start();
        threadB.start();

    }

    static class PrintRunnable implements Runnable {

        private String content;
        private Long millis;

        public PrintRunnable(String content, Long millis) {
            this.content = content;
            this.millis = millis;
        }

        @Override
        public void run() {
            while (true) {
                log(content);
                try {
                    sleep(millis);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
