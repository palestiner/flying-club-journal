package org.palestiner.flyingclubjournal.screen.cadet;

import io.jmix.core.DataManager;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.Cadet;
import org.palestiner.flyingclubjournal.entity.Rate;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Cadet.browse")
@UiDescriptor("cadet-browse.xml")
@LookupComponent("cadetsTable")
public class CadetBrowse extends StandardLookup<Cadet> {
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private CollectionContainer<Rate> ratesDc;
    @Autowired
    private GroupTable<Cadet> cadetsTable;
    @Autowired
    private DataManager dataManager;

    @Install(to = "cadetsTable.rate", subject = "columnGenerator")
    private Component cadetsTableRateColumnGenerator(Cadet cadet) {
        ComboBox<Rate> rateComboBox = uiComponents.create(ComboBox.of(Rate.class));
        rateComboBox.setNullOptionVisible(false);
        rateComboBox.setValue(cadet.getRate());
        rateComboBox.setOptionsList(ratesDc.getItems());
        rateComboBox.addValueChangeListener(rateValueChangeEvent -> {
            cadet.setRate(rateValueChangeEvent.getValue());
            dataManager.save(cadet);
            cadetsTable.repaint();
        });
        return rateComboBox;
    }
}
