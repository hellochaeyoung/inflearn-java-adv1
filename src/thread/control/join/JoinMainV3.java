package thread.control.join;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinMainV3 {

    public static void main(String[] args) throws InterruptedException {
        log("Start");
        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);
        Thread thread1 = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task2, "thread-2");

        thread1.start(); // start 호출하면 스레드 내 스택 생성 및 스택 프레임(run() 메소드) 쌓이고 run() 코드 동작, 힙 영역에 각 인스턴스 결과값 저장
        thread2.start();

        // 스레드 1,2 다 종료될때까지 메인 스레드 대기
        log("join() - main 스레드가 thread1, thread2 종료까지 대기");
        thread1.join(); // 메인스레드는 여기서 thread1이 종료될때 까지 대기, 다음으로 안넘어감
        thread2.join(); // thread1이 종료되서 여기로 넘어왔을 대 이미 thread2가 종료상태이면 바로 메인 스레드는 러너블 상태가됨
        log("join() - main 스레드 대기 완료");

        log("task1.result = " + task1.result);
        log("task2.result = " + task2.result);

        int sumAll = task1.result + task2.result;
        log("task1 + task2 = " + sumAll);

        log("End");
    }

    static class SumTask implements Runnable {

        int startValue;
        int endValue;
        int result;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public void run() {
            log("작업 시작");
            int sum = 0;
            sleep(2000);
            for (int i = startValue; i <= endValue; i++) {
                sum += i;
            }
            result = sum;
            log("작업 완료 result = " + result);

        }
    }
}
