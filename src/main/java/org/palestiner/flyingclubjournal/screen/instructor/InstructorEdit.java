package org.palestiner.flyingclubjournal.screen.instructor;

import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.Instructor;

@UiController("Instructor.edit")
@UiDescriptor("instructor-edit.xml")
@EditedEntityContainer("instructorDc")
public class InstructorEdit extends StandardEditor<Instructor> {
}
