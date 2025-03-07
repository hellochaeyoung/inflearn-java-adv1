package thread.start;

public class HelloThreadMain {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ": main() start");

        HelloThread helloThread = new HelloThread();
        System.out.println(Thread.currentThread().getName() + ": start() 호출 전");
        helloThread.start(); // 스레드 객체 생성 후 start 호출해야 스택 공간 할당, 스레드 작동
        // 메인 스레드는 helloThread에게 일 하라고 지시 후 끝날때까지 기다려주지않음 바로 다음 메인 메소드 코드 실행
        // 로그 출력 순서를 보면 알 수 있음
        // 하지만, 스레드는 동시에 실행되기 때문에 실행할 때 마다 스레드 간의 실행순서가 달라지기 때문에 로그 출력 위치도 바뀐다!

        System.out.println(Thread.currentThread().getName() + ": start() 호출 후");

        System.out.println(Thread.currentThread().getName() + ": main() end");

        // 스레드 호출과 실행 순서는 보장되지 않는다!
    }
}
