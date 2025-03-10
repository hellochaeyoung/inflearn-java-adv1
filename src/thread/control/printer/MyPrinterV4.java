package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static util.MyLogger.log;

public class MyPrinterV4 {
    public static void main(String[] args) {

        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");
        printerThread.start();

        Scanner userInput = new Scanner(System.in);
        while (true) {
            log("프린터할 문서를 입력하세요. 종료 (q): ");
            String input = userInput.nextLine();
            if(input.equals("q")) {
                printerThread.interrupt();
                break;
            }

            printer.addJob(input);
        }

    }

    static class Printer implements Runnable {
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while(!Thread.interrupted()) {
                if(jobQueue.isEmpty()) { // 잡 체크 로직에 cpu가 너무많은 연산을 수행함, 스레드가 아주 많다고 가정했을 때, yield를 통해 다른 스레드 일할 수 있도록 추가
                    Thread.yield();
                    continue;
                }

                try {
                    String job = jobQueue.poll();
                    log("출력 시작: " + job + ", 대기 문서: " + jobQueue);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    log("인터럽트!");
                    break;
                }
                log("출력 완료");
            }

            log("출력 종료");
        }

        public void addJob(String job) {
            jobQueue.add(job);
        }
    }
}
