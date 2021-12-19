package org.palestiner.flyingclubjournal.screen.flighttype;

import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.FlightType;

@UiController("FlightType.edit")
@UiDescriptor("flight-type-edit.xml")
@EditedEntityContainer("flightTypeDc")
public class FlightTypeEdit extends StandardEditor<FlightType> {
}
