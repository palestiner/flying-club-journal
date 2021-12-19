package org.palestiner.flyingclubjournal.screen.fightaccounting;

import io.jmix.ui.component.ComboBox;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.FightAccounting;
import org.palestiner.flyingclubjournal.entity.FlightType;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("FightAccounting.edit")
@UiDescriptor("fight-accounting-edit.xml")
@EditedEntityContainer("fightAccountingDc")
public class FightAccountingEdit extends StandardEditor<FightAccounting> {
    @Autowired
    private ComboBox<FlightType> flightTypeField;
    @Autowired
    private CollectionLoader<FlightType> flightTypesDl;
    @Autowired
    private CollectionContainer<FlightType> flightTypesDc;

    @Subscribe
    public void onInit(InitEvent event) {
        flightTypesDl.load();
        flightTypeField.setOptionsList(flightTypesDc.getItems());
    }

}
