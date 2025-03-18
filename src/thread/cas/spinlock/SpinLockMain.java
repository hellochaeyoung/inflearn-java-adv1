package thread.cas.spinlock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class SpinLockMain {

    public static void main(String[] args) {
        //SpinLockBad spinLock = new SpinLockBad();
        SpinLock spinLock = new SpinLock();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                spinLock.lock();
                try {
                    log("비즈니스 로직 실행");
                    sleep(1000); // 비즈니스 로직 실행하는데 오래걸린다 가정 -> 이런 경우에는 CAS, 즉 스핀 락 사용하면 안됨
                } finally {
                    spinLock.unlock();
                }

            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();
    }
}
