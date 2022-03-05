package org.palestiner.flyingclubjournal.service;

import org.palestiner.flyingclubjournal.entity.FightAccounting;

public interface CalcMoneyService {
    double calculateAccrued(FightAccounting flight);
}
