package thread.control.volatile1;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class VolatileFlagMain {
    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        log("runFlag = " + task.runFlag);
        thread.start();

        sleep(1000);
        log("runFlag를 false로 변경 시도");
        task.runFlag = false;
        log("runFlag = " + task.runFlag);
        log("main 종료");

    }

    static class MyTask implements Runnable {

        //boolean runFlag = true;
        volatile boolean runFlag = true; // 캐시 메모리에서 안보고 메인 메모리에 직접 접근해서 확인

        @Override
        public void run() {
            log("task 시작");
            while(runFlag) {
                // 여기에 코드가 있으면 컨텍스트 스위칭 되면서 캐시 메모리를 다시 불러오기 때문에 volatile 키워드 없어도 값 확인 후 스레드 정상 종료될 수 있다!
            }
            log("task 종료");
        }
    }
}
