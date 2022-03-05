package org.palestiner.flyingclubjournal.listener;

import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.jmix.core.event.EntitySavingEvent;
import org.palestiner.flyingclubjournal.entity.FightAccounting;
import org.palestiner.flyingclubjournal.entity.MoneyAccounting;
import org.palestiner.flyingclubjournal.entity.Rate;
import org.palestiner.flyingclubjournal.service.CalcMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RateEventListener {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private CalcMoneyService calcMoneyService;

    @EventListener
    public void onRateSaving(EntitySavingEvent<Rate> event) {
        Rate rate = event.getEntity();
        List<MoneyAccounting> mas = dataManager
                .load(MoneyAccounting.class)
                .query("select ma from MoneyAccounting ma where ma.cadet.rate = :rate")
                .fetchPlan("MA_for_Rate")
                .parameter("rate", rate)
                .list();
        mas.forEach(moneyAccounting -> {
            moneyAccounting.setAccrued(setAccrued(moneyAccounting.getId()));
        });
        dataManager.save(new SaveContext()
                .saving(mas)
                .setJoinTransaction(false));
    }

    private double setAccrued(UUID maId) {
        FightAccounting fa = dataManager
                .load(FightAccounting.class)
                .query("select fa from FightAccounting fa where fa.moneyAccounting.id = :maId")
                .parameter("maId", maId)
                .one();
        return calcMoneyService.calculateAccrued(fa);
    }
}
