package org.palestiner.flyingclubjournal.service;

import org.palestiner.flyingclubjournal.entity.FightAccounting;
import org.springframework.stereotype.Component;

@Component
public class CalcMoneyServiceImpl implements CalcMoneyService {
    @Override
    public double calculateAccrued(FightAccounting flight) {
        Double flightTime = flight.getFlightTime();
        Double price = flight.getCadet().getRate().getPrice();
        Double discount = flight.getCadet().getDiscount();
        return (double) Math.round(price * flightTime * (discount != null ? (100 - discount) / 100 : 1) / 60);
    }
}
