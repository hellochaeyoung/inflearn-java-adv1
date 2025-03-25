package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureExceptionMain {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log("작업 전달");
        Future<Integer> future = es.submit(new ExCallable());
        sleep(1000); // 대기


        try {
            log("future.get() 호출 시도, future.state():" + future.state());
            Integer result = future.get(); // 여기, 요청 스레드가 꺼내려 하는 순간 ExecutionException 발생(예외로 실패된 Callable, 즉 작업스레드가 future에 결과값인 예외를 담아두기 때문에 꺼내려하면 예외발생)
            log("result value = " + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log("e = " + e);
            Throwable cause = e.getCause();
            log("cause = " + cause); // 원본 예외를 출력
        }
        es.close();
    }

    static class ExCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            log("Callable 실행, 예외 발생");
            throw new IllegalStateException("ex!");
        }
    }
}
