package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void equals() {
        Deadline deadline = new Deadline("01 January 2019");

        //same object -> returns true
        assertTrue(deadline.equals(deadline));

        //same values -> returns true
        Deadline deadlineCopy = new Deadline(deadline.value);
        assertTrue(deadline.equals(deadlineCopy));

        //different types -> returns false
        assertFalse(deadline.equals(1));

        //null -> returns false
        assertFalse(deadline.equals(null));

        //different person -> returns false
        Deadline differentDeadline = new Deadline("31 December 2019");
        assertFalse(deadline.equals(differentDeadline));
    }
}
