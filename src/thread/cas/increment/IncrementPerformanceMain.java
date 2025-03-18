package thread.cas.increment;

public class IncrementPerformanceMain {

    public static final long COUNT = 100_000_000;

    public static void main(String[] args) {
        // CPU 캐시 적극 활용 -> 가장빠름, 멀티스레드에서는 사용불가(임계영역 없기 때문), 단일스레드에서 효율적
        test(new BasicInteger());

        // 메인 메모리 사용하기 때문에 속도도 느림, 멀티스레드에서 사용불가(임계영역 없기 때문), 단일스레드에서는 속도도 느려서 비효율적
        test(new VolatileInteger());

        // 임계 영역 있어서 멀티스레드 사용 가능, but 느리다
        test(new SyncInteger());

        // 멀티 스레드에서 사용 가능, 성능도 synchronized, Lock(ReentrantLock) 보다 빠름
        // 어떻게 빠르지!?!? => AtomicInteger의 incrementAndGet() 메소드는 락 사용 안하고 원자적 연산 만들어냄 -> CAS
        test(new MyAtomicInteger());
    }

    private static void test(IncrementInteger incrementInteger) {
        long startMs = System.currentTimeMillis();

        // 1억번 증가 메소드 호출해서 각 Integer 클래스들의 연산 성능 확인
        for (long i = 0; i < COUNT; i++) {
            incrementInteger.increment();
        }

        long endMs = System.currentTimeMillis();
        System.out.println(incrementInteger.getClass().getSimpleName() + ": ms=" + (endMs - startMs));
    }
}
