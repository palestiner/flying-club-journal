package org.palestiner.flyingclubjournal.screen.moneyaccounting;

import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.MoneyAccounting;

@UiController("MoneyAccounting.browse")
@UiDescriptor("money-accounting-browse.xml")
@LookupComponent("moneyAccountingsTable")
public class MoneyAccountingBrowse extends StandardLookup<MoneyAccounting> {
}
