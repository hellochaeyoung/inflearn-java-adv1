package thread.quiz.d7;

import static util.MyLogger.log;

public class SyncTest2Main {

    public static void main(String[] args) {
        MyCounter myCounter = new MyCounter();

        Runnable task = new Runnable() {
            public void run() {
                myCounter.count(); // 동일 인스턴스를 사용, 힙 메모리에 저장
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");

        thread1.start();
        thread2.start();
    }

    static class MyCounter {
        public void count() {
            int localValue = 0; // 지역 변수, 스택 영역에 저장, 스레드는 스택 영역을 각각 가짐! 스택 영역은 절대 공유되지 않음
            for (int i = 0; i < 1000; i++) {
                localValue = localValue + 1;
            }
            log("결과: " + localValue); }
    }
}
