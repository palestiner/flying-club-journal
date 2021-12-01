package org.palestiner.flyingclubjournal.screen.rate;

import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.Rate;

@UiController("Rate.browse")
@UiDescriptor("rate-browse.xml")
@LookupComponent("ratesTable")
public class RateBrowse extends StandardLookup<Rate> {
}
