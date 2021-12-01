package org.palestiner.flyingclubjournal.screen.airplane;

import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.Airplane;

@UiController("Airplane.edit")
@UiDescriptor("airplane-edit.xml")
@EditedEntityContainer("airplaneDc")
public class AirplaneEdit extends StandardEditor<Airplane> {
}
