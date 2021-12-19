package org.palestiner.flyingclubjournal.screen.instructor;

import io.jmix.ui.screen.*;
import org.palestiner.flyingclubjournal.entity.Instructor;

@UiController("Instructor.browse")
@UiDescriptor("instructor-browse.xml")
@LookupComponent("instructorsTable")
public class InstructorBrowse extends StandardLookup<Instructor> {
}
