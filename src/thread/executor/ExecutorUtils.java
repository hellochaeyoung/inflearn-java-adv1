package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static util.MyLogger.log;

public abstract class ExecutorUtils {

    public static void printState(ExecutorService executorService) {
        if(executorService instanceof ThreadPoolExecutor poolExecutor) {
            int poolSize = poolExecutor.getPoolSize();
            int activeCount = poolExecutor.getActiveCount();
            int queue = poolExecutor.getQueue().size(); // 큐에 대기중인 작업 수
            long completedTaskCount = poolExecutor.getCompletedTaskCount();
            log("[pool=" + poolSize + ", activeCount=" + activeCount + ", queue=" + queue + ", completedTaskCount=" + completedTaskCount + "]");
        } else {
            log(executorService);
        }
    }
}
