package org.palestiner.flyingclubjournal.service;

import org.palestiner.flyingclubjournal.entity.FightAccounting;

public interface CalcMoneyService {
    Double calculateAccrued(FightAccounting flight);
}
