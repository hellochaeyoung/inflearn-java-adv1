package thread.start;

public class BadThreadMain {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ": main() start");

        HelloThread helloThread = new HelloThread();
        System.out.println(Thread.currentThread().getName() + ": start() 호출 전");
        helloThread.run(); // 직접 run 메소드 호출 시 HelloThread 아닌 main 스레드가 run 호출
        // start() 메소드는 스레드에 스택 공간 할당하면서 스레드 시작하는 메소드!
        // 바로 run 메소드 호출 시 main 스레드가 수행하게 됨, 원하는 스레드의 메소드를 호출하고 싶으면 start 메소드 사용 필요

        System.out.println(Thread.currentThread().getName() + ": start() 호출 후");

        System.out.println(Thread.currentThread().getName() + ": main() end");
    }
}
