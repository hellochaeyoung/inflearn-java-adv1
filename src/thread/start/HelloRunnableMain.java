package thread.start;

public class HelloRunnableMain {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ": main() start");

        HelloRunnable runnable = new HelloRunnable();
        Thread thread = new Thread(runnable); // 작업과 스레드를 분리 => 스레드와 해당 스레드가 실행할 작업이 분리되어 있다!
        thread.start();

        System.out.println(Thread.currentThread().getName() + ": main() end");
    }
}
