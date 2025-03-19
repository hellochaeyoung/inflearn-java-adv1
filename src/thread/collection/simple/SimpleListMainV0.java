package thread.collection.simple;

import java.util.ArrayList;
import java.util.List;

public class SimpleListMainV0 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        // 스레드 1,2가 동시에 실행했다 가정
        list.add("A"); // 스레드 1 -> add 메소드가 원자적인 연산일까? -> X
        list.add("B"); // 스레드 2
        System.out.println(list);
    }
}
