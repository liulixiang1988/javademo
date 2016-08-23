package lx.spring.core.services;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

/**
 * Created by liulixiang on 16/8/19.
 */
public class SimpleService {
    private final TransactionTemplate transactionTemplate;

    public SimpleService(PlatformTransactionManager transactionManager) {
        Assert.notNull(transactionManager, "The 'transactionManager' argument must not be null");
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        //这里我们可以设置会话
        this.transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
        this.transactionTemplate.setTimeout(30); //30秒
    }

    public Object someServiceMethod() {
        return transactionTemplate.execute((TransactionStatus status) -> {
            System.out.println("hello");
            return "aaa";
        });
    }

    public void someServiceMethodWithouResult() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                System.out.println(status);
            }
        });
    }
}
