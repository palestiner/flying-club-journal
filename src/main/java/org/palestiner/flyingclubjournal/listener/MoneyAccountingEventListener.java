package org.palestiner.flyingclubjournal.listener;

import org.palestiner.flyingclubjournal.entity.MoneyAccounting;
import io.jmix.core.event.EntityLoadingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MoneyAccountingEventListener {

    @EventListener
    public void onMoneyAccountingLoading(EntityLoadingEvent<MoneyAccounting> event) {
        event.getEntity().setTotal(calcTotal(event.getEntity()));
    }

    private double calcTotal(MoneyAccounting money) {
        return money.getAccrued() - money.getPaid();
    }
}
