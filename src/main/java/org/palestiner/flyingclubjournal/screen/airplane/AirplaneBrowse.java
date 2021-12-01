package org.palestiner.flyingclubjournal.screen.airplane;

import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.Airplane;

@UiController("Airplane.browse")
@UiDescriptor("airplane-browse.xml")
@LookupComponent("airplanesTable")
public class AirplaneBrowse extends StandardLookup<Airplane> {
}
