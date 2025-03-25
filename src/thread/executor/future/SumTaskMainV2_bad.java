package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;

public class SumTaskMainV2_bad {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SumTask task1 = new SumTask(1,50);
        SumTask task2 = new SumTask(51,100);

        ExecutorService es = Executors.newFixedThreadPool(2);

        Future<Integer> future1 = es.submit(task1); // non-blocking
        Integer sum1 = future1.get(); // future1에 결과 담길때까지 2초 대기

        Future<Integer> future2 = es.submit(task2); // future1 결과 받고 나서 task2 실행
        Integer sum2 = future2.get(); // future2에 결과 담길때까지 2초 대기 => 이렇게 실행하면 싱글 스레드와 동일하게 동작, 멀티스레드 사용 의미가 없다

        log("task1.result=" + sum1);
        log("task2.result=" + sum2);

        int sumAll = sum1 + sum2;
        log("task1 + task2 = " + sumAll);
        log("End");

        es.close();
    }

    static class SumTask implements Callable<Integer> {
        int startValue;
        int endValue;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public Integer call() throws Exception {
            log("작업 시작");
            Thread.sleep(2000);
            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                sum += i;
            }
            log("작업 완료 result=" + sum);
            return sum;
        }
    }
}
