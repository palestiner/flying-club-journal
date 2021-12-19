package org.palestiner.flyingclubjournal.screen.cadet;

import io.jmix.core.DataManager;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.Cadet;
import org.palestiner.flyingclubjournal.entity.Rate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("Cadet.edit")
@UiDescriptor("cadet-edit.xml")
@EditedEntityContainer("cadetDc")
public class CadetEdit extends StandardEditor<Cadet> {
    @Autowired
    protected DataManager dataManager;
    @Autowired
    private ComboBox<Rate> rateField;
    @Autowired
    private CollectionContainer<Rate> ratesDc;
    @Autowired
    private CollectionLoader<Rate> ratesDl;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Subscribe
    public void onInit(InitEvent event) {
        ratesDl.load();
        List<Rate> items = ratesDc.getItems();
        rateField.setOptionsList(items);
    }

    @Subscribe("createRate")
    public void onCreateRateClick(Button.ClickEvent event) {
        Screen rateEdit = screenBuilders
                .editor(Rate.class, this)
                .editEntity(dataManager.create(Rate.class))
                .withContainer(ratesDc)
                .withParentDataContext(ratesDl.getDataContext())
                .build();
        rateEdit.addAfterCloseListener(afterCloseEvent -> {
            rateField.setOptionsList(ratesDc.getItems());
        });
        rateEdit.show();
    }
}
