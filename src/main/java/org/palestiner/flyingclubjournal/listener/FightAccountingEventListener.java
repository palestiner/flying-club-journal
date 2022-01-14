package org.palestiner.flyingclubjournal.listener;

import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntitySavingEvent;
import org.palestiner.flyingclubjournal.entity.FightAccounting;
import org.palestiner.flyingclubjournal.entity.MoneyAccounting;
import org.palestiner.flyingclubjournal.service.CalcMoneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

@Component
public class FightAccountingEventListener {
    private static final Logger log = LoggerFactory.getLogger(FightAccountingEventListener.class);

    @Autowired
    private DataManager dataManager;
    @Autowired
    private CalcMoneyService calcMoneyService;

    @TransactionalEventListener
    public void onChangedAfterCommit(EntityChangedEvent<FightAccounting> event) {
        try {
            FightAccounting flight = getFightAccounting(event);
            if (event.getType() != EntityChangedEvent.Type.DELETED) handleFlightAcc(flight);
        } catch (Exception e) {
            log.error("Error on flight accounting changed after commit", e);
        }
    }

    @EventListener
    public void onFightAccountingChangedBeforeCommit(EntityChangedEvent<FightAccounting> event) {
        try {
            Object moneyAccounting = dataManager
                    .load(Objects.requireNonNull(event.getChanges().getOldReferenceId("moneyAccounting")))
                    .one();
            if (event.getType() == EntityChangedEvent.Type.DELETED)
                dataManager.remove(moneyAccounting);
        } catch (Exception e) {
            log.error("Error on flight accounting changed before commit", e);
        }
    }

    @EventListener
    public void onSaving(EntitySavingEvent<FightAccounting> event) {
        if (event.isNewEntity()) {
            FightAccounting flightAcc = event.getEntity();
            MoneyAccounting moneyAccounting = dataManager.create(MoneyAccounting.class);
            copyOption(flightAcc, moneyAccounting);
            saveMoneyAcc(moneyAccounting);
            flightAcc.setMoneyAccounting(moneyAccounting);
        }
    }

    private FightAccounting getFightAccounting(EntityChangedEvent<FightAccounting> event) {
        return dataManager.load(event.getEntityId())
                .joinTransaction(false)
                .fetchPlan("FA_for_FM")
                .one();
    }

    @Transactional
    void handleFlightAcc(FightAccounting flight) {
        MoneyAccounting money = dataManager
                .load(MoneyAccounting.class)
                .id(flight.getMoneyAccounting().getId())
                .joinTransaction(false)
                .one();
        handleAndSaveMoney(flight, money);
    }

    private void handleAndSaveMoney(FightAccounting flight, MoneyAccounting money) {
        copyOption(flight, money);
        saveMoneyAcc(money);
    }

    private void saveMoneyAcc(MoneyAccounting money) {
        dataManager.save(new SaveContext()
                .saving(money)
                .setJoinTransaction(false));
    }

    private void copyOption(FightAccounting from, MoneyAccounting to) {
        to.setCadet(from.getCadet());
        to.setFlightDate(fromDateToLocalDate(from));
        to.setAccrued(calcMoneyService.calculateAccrued(from));
        to.setPaid(to.getPaid() != null ? to.getPaid() : 0d);
    }

    private LocalDate fromDateToLocalDate(FightAccounting flight) {
        return flight
                .getFlightDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
