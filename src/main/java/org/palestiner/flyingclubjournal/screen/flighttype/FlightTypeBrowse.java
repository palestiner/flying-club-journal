package org.palestiner.flyingclubjournal.screen.flighttype;

import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.FlightType;

@UiController("FlightType.browse")
@UiDescriptor("flight-type-browse.xml")
@LookupComponent("flightTypesTable")
public class FlightTypeBrowse extends StandardLookup<FlightType> {
}
