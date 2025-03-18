package thread.cas.increment;

public class VolatileInteger implements IncrementInteger {

    // volatile은 스레드 간의 메모리 가시성을 해결, 연산 자체를 원자적으로 묶어주지 X
    volatile private int value;

    @Override
    public void increment() {
        // 이 코드의 연산은 원자적 연산 X, 두 단계로 나뉘어져 있음, 따라서 volatile을 사용해도 해결 X
        value++;
    }

    @Override
    public int get() {
        return value;
    }
}
