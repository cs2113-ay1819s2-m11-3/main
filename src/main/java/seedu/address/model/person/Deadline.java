package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a subject's deadline in the address book.
 * Guarantees: immutable; is always valid
 */

public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Person deadlines can take any values, can even be blank";

    public final String value;

    public Deadline(String deadline) {
        requireNonNull(deadline);
        value = deadline;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
            || (other instanceof Deadline // instanceof handles nulls
            && value.equals(((Deadline) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
