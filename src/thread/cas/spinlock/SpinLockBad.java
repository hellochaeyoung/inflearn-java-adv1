package thread.cas.spinlock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class SpinLockBad {

    private volatile boolean lock = false;

    public void lock() {
        log("락 획득 시도");
        while(true) {
            // 락을 확인하고 변경하는 과정이 원자적인 연산이 아니기 때문에 현재 버전에서는 멀티스레드인 경우 실패
            // synchronized나 Lock을 사용해서 안전한 임계 영역을 만들거나
            // CAS 방식을 사용해 두 단계를 하나의 원자적인 연산으로 처리되도록 하거나
            if(!lock) { // 1. 락 여부 확인
                sleep(100);
                lock = true; // 2. 락 값 변경
                break;
            } else {
                // wait, notify 사용해서 대기 상태로 만들지 않고 계속 Runnable 상태로 대기하도록
                log("락 획득 실패 - 스핀 대기");
            }
        }

        log("락 획득 완료");
    }

    public void unlock() {
        lock = false;
        log("락 반납 완료");
    }
}
