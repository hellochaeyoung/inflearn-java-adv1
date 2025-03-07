package thread.start;

public class DemonThreadMain {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ": main() start");

        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true);
        daemonThread.start();

        System.out.println(Thread.currentThread().getName() + ": main() end");
        // 바로 종료됨, 데몬 스레드여서 사용자 스레드 아니기 때문에 메인 스레드 종료 시
        // 모든 사용자 스레드 종료됐다고 판단하기 때문에 데몬 스레드 종료시키면서 자바도 종료
    }

    static class DaemonThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": run()");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName() + ": run() end");
        }
    }
}
