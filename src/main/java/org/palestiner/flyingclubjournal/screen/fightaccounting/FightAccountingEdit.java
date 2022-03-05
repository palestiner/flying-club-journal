package org.palestiner.flyingclubjournal.screen.fightaccounting;

import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.palestiner.flyingclubjournal.entity.FightAccounting;

@UiController("FightAccounting.edit")
@UiDescriptor("fight-accounting-edit.xml")
@EditedEntityContainer("fightAccountingDc")
public class FightAccountingEdit extends StandardEditor<FightAccounting> {
}
