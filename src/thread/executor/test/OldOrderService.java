package thread.executor.test;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
public class OldOrderService {

    private final ExecutorService es = Executors.newFixedThreadPool(3);

    public void order(String orderNo) throws InterruptedException {

        InventoryWork inventoryWork = new InventoryWork(orderNo);
        ShippingWork shippingWork = new ShippingWork(orderNo);
        AccountingWork accountingWork = new AccountingWork(orderNo);

        List<Callable<Boolean>> works = List.of(inventoryWork, shippingWork, accountingWork);

        AtomicReference<Boolean> totalResult = new AtomicReference<>(Boolean.TRUE);
        List<Future<Boolean>> futures = es.invokeAll(works);
        futures.forEach(future -> {
            try {
                Boolean result = future.get();
                if(!result) {
                    totalResult.set(result);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        if(totalResult.get()) {
            log("모든 주문 처리가 성공적으로 완료되었습니다.");
        } else {
            log("일부 작업이 실패했습니다.");
        }
    }

    public void close() {
        es.close();
    }

    static class InventoryWork implements Callable<Boolean> {
        private final String orderNo;
        public InventoryWork(String orderNo) {
            this.orderNo = orderNo;
        }
        public Boolean call() {
            log("재고 업데이트: " + orderNo); sleep(1000);
            return true;
        }
    }

    static class ShippingWork implements Callable<Boolean> {
        private final String orderNo;
        public ShippingWork(String orderNo) {
            this.orderNo = orderNo;
        }
        public Boolean call() {
            log("배송 시스템 알림: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class AccountingWork implements Callable<Boolean> {
        private final String orderNo;
        public AccountingWork(String orderNo) {
            this.orderNo = orderNo;
        }
        public Boolean call() {
            log("회계 시스템 업데이트: " + orderNo); sleep(1000);
            return true;
        }
    }

}