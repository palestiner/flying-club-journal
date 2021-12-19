package org.palestiner.flyingclubjournal.screen.fightaccounting;

import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.FightAccounting;

@UiController("FightAccounting.browse")
@UiDescriptor("fight-accounting-browse.xml")
@LookupComponent("fightAccountingsTable")
public class FightAccountingBrowse extends StandardLookup<FightAccounting> {
}
