 package thread.bounded;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static util.MyLogger.log;

public class BoundedQueueV6_4 implements BoundedQueue {

    private BlockingQueue<String> queue;

    // 가득 차거나, 없을 때 즉시 예외 반환, 대기 X
    public BoundedQueueV6_4(int max) {
        this.queue = new ArrayBlockingQueue<>(max);
    }

    @Override
    public void put(String data) {
        queue.add(data); // queue full -> 즉시 throw Exception
    }

    @Override
    public String take() {
        return queue.remove(); // 즉시 NoSuchElementException
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
