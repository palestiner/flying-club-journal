package org.palestiner.flyingclubjournal.screen.rate;

import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.Rate;

@UiController("Rate.edit")
@UiDescriptor("rate-edit.xml")
@EditedEntityContainer("rateDc")
public class RateEdit extends StandardEditor<Rate> {
}
