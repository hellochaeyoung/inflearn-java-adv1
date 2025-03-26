package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;

public class ExecutorShutdownMain {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("longTask", 100_000)); // 100초 대기, 잘못된 테스크용
        printState(es);
        log("== shutdown 시작");
        shutdownAndAwaitTermination(es);
        log("== shutdown 완료");
        printState(es);
    }

    private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown(); // 정상 종료 시도, 논블로킹, 새로운 작업 받지 않음, 처리중/대기중인 작업까지만 처리, 이후 풀의 스레드 종료
        try {
            // 10초 동안 모든 처리중/대기중 작업 끝나길 대기, shutdown은 논블로킹이기 때문에 호출한 스레드는 바로 다음 코드 실행해서 여기서 대기
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) { // 블로킹
                // 정상 종료 너무 오래 걸리면
                log("서비스 정상 종료 실패, 너무 오래걸림 -> 강제 종료 시도");
                es.shutdownNow(); // 마찬가지로 논블로킹

                // 작업이 취소될 때까지 대기
                if(!es.awaitTermination(10, TimeUnit.SECONDS)) { // 블로킹, 설정한 시간만큼 대기
                    log("서비스가 종료되지 않았습니다.");
                }
            }

        } catch (InterruptedException e) {
            // awaitTermination()으로 대기중인 현재 스레드를 메인스레드가 아예 인터럽트 시킬 수 있다.
            es.shutdownNow();
        }

    }
}
