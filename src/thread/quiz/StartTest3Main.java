package thread.quiz;

import static java.lang.Thread.sleep;
import static util.MyLogger.log;

public class StartTest3Main {
    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    log("value: " + i);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "counter");
        //thread.setName("counter");
        thread.start();
    }
}
