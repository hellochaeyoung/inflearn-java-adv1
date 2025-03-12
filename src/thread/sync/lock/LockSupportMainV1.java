package thread.sync.lock;

import java.util.concurrent.locks.LockSupport;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class LockSupportMainV1 {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new ParkTest(), "Thread-1");
        thread1.start();

        // 잠시 대기하고 스레드-1이 park 상태에 빠질 시간 줌
        sleep(100);
        log("Thread-1 state: " + thread1.getState());

        log("main -> unpark(Thread-1)");
//        LockSupport.unpark(thread1); // 1. unpark 사용
        thread1.interrupt(); // 2. interrupt() 사용, WAITING 상태의 스레드를 인터럽트 걸어서 중간에 깨우기 가능

        // BLOCKING 상태의 스레드는 interrupt로 깨울 수 없음
        // synchronized를 사용하면 BLOCKED 상태가 되서 무한정 대기하지만 LockSupport를 사용하면 이 단점을 해결 가능
    }

    static class ParkTest implements Runnable {

        @Override
        public void run() {
            log("park 시작");
            LockSupport.park(); // 스레드 상태: RUNNABLE -> WAITING으로 변경
            log("park 종료, state: " + Thread.currentThread().getState());
            log("인터럽트 상태: " + Thread.currentThread().isInterrupted());
        }
    }
}
