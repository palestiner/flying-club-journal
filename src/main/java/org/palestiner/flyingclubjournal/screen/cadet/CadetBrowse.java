package org.palestiner.flyingclubjournal.screen.cadet;

import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.palestiner.flyingclubjournal.entity.Cadet;

@UiController("Cadet.browse")
@UiDescriptor("cadet-browse.xml")
@LookupComponent("cadetsTable")
public class CadetBrowse extends StandardLookup<Cadet> {
}
