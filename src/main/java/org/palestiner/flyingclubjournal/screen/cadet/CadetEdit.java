package org.palestiner.flyingclubjournal.screen.cadet;

import io.jmix.ui.component.ComboBox;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.Cadet;
import org.palestiner.flyingclubjournal.entity.Rate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("Cadet.edit")
@UiDescriptor("cadet-edit.xml")
@EditedEntityContainer("cadetDc")
public class CadetEdit extends StandardEditor<Cadet> {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CadetEdit.class);
    @Autowired
    private ComboBox<Rate> rateField;
    @Autowired
    private CollectionContainer<Rate> ratesDc;
    @Autowired
    private CollectionLoader<Rate> ratesDl;

    @Subscribe
    public void onInit(InitEvent event) {
        ratesDl.load();
        List<Rate> items = ratesDc.getItems();
        rateField.setOptionsList(items);
    }


}
