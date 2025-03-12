package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankAccountV2 implements BankAccount {

    private int balance;

    public BankAccountV2(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public synchronized boolean withdraw(int amount) { // 임계 영역, 한 번에 한 스레드만 호출 가능, 누가 출금 하고 있으면 조회 메소드도 호출 불가
        log("거래 시작: " + getClass().getSimpleName());

        // 현재 버전에서는 메소드 단위로 임계 영역 지정
        // 임계 영역 시작
        // 공유 자원의 값을 조회하고 변경하는 지점까지 임계 영역
        log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
        if(balance < amount) {
            log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
            return false;
        }

        log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
        sleep(1000); // 출금 하는데 걸리는 시간으로 가정
        balance -= amount;
        log("[출금 완료] 출금액: " + amount + ", 잔액: " + balance);
        // 임계 영역 종료

        log("거래 종료");
        return true;
    }

    @Override
    public synchronized int getBalance() {
        return balance;
    }
}
