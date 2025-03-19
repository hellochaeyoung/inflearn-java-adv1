package thread.collection.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static util.ThreadUtils.sleep;

public class SynchronizedListMain {
    public static void main(String[] args) throws InterruptedException {

        // java에서 제공하는 동기화 처리된 리스트 기능하게 하는 프록시
        // 단순하게 모든 컬렉션 전체, 즉 메소드에 synchronized 적용한 것으로 과한 동기화로 인해 오버헤드 또는 성능 저하 발생 가능
        // => 자바에서 이런 단점을 보완하기 위해 concurrent 패키지에 동시성 컬렉션을 제공
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        list.add("data1");
        list.add("data2");
        list.add("data3");
        System.out.println(list.getClass());
        System.out.println("list = " + list);

        Map<String, Integer> map = new ConcurrentHashMap<>();
        test(map);

        System.out.println(map);
    }

    public static void test(Map<String, Integer> map) throws InterruptedException {


        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                map.put("data1", map.getOrDefault("data1", 0) + 1);
                sleep(1000);
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                map.put("data1", map.getOrDefault("data1", 0) + 1);
                sleep(1000);
            }
        };

        Thread thread1 = new Thread(runnable1, "Thread-1");
        Thread thread2 = new Thread(runnable2, "Thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
