package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ExecutorBasicMain {
    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(2,2,0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        log("== 초기 상태 ==");
        printState(es);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB")); // pool 사이즈 2개이기 때문에 여기까진 스레드 생성
        es.execute(new RunnableTask("taskC")); // 여기부터는 생성된 스레드 재사용
        es.execute(new RunnableTask("taskD"));
        log("== 작업 수행 중 ==");
        printState(es);

        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(es);

        es.close(); // ThreadPoolExecutor 종료, 스레드 풀에 대기상태인 스레드도 같이 제거
        log("== shutdown 완료 ==");
        printState(es);
    }
}
