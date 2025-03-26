package thread.executor.poolsize;

import thread.executor.RunnableTask;

import java.util.concurrent.*;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class PoolSizeMainV1 {
    public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ExecutorService es = new ThreadPoolExecutor(2, 4,
                3000, TimeUnit.MILLISECONDS, workQueue);
        printState(es);

        es.execute(new RunnableTask("task1")); // 이 전까지 스레드풀에 스레드 없음, 처음 요청 시, 따라서 첫 요청 때 스레드 하나 생성하고 작업은 큐에 넣어서 대기할 필요 없이 생성된 스레드가 작업 처리
        printState(es, "task1");

        es.execute(new RunnableTask("task2"));
        printState(es, "task2");

        es.execute(new RunnableTask("task3"));
        printState(es, "task3");

        es.execute(new RunnableTask("task4"));
        printState(es, "task4");

        es.execute(new RunnableTask("task5")); // 대기 큐에도 최대 개수까지 차있는 이때부터 스레드 풀의 개수를 늘린다.
        printState(es, "task5");

        es.execute(new RunnableTask("task6"));
        printState(es, "task6");

        try {
            es.execute(new RunnableTask("task7"));
        } catch (RejectedExecutionException e) {
            // 대기 큐 최대 대기(2개), 스레드 풀 최대 개수(4)까지 생성되었기 때문에 추가로 작업을 더 수행할 수 없어서 예외 발생
            log("task7 실행 거절 예외 발생: " + e);
        }

        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(es);

        sleep(3000);
        log("== maximumPoolSize 대기 시간 초과 =="); // 스레드 대기시간(keepAliceTime) 만큼 일이 없으면 기본 개수보다 초과되어 생성된 스레드들은 제거됨
        printState(es);

        es.close(); // 기본 스레드까지 모두 종료
        log("== shutdown 완료 == ");
        printState(es);
    }
}
