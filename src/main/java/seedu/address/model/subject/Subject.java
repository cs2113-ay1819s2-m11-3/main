package seedu.address.model.subject;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Subject in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Subject {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Deadline deadline;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Subject(Name name, Phone phone, Email email, Address address, Deadline deadline, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.deadline = deadline;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both subjects of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two subjects.
     */
    public boolean isSameSubject(Subject otherSubject) {
        if (otherSubject == this) {
            return true;
        }

        return otherSubject != null
                && otherSubject.getName().equals(getName())
                && (otherSubject.getPhone().equals(getPhone()) || otherSubject.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both subjects have the same identity and data fields.
     * This defines a stronger notion of equality between two subjects.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Subject)) {
            return false;
        }

        Subject otherSubject = (Subject) other;
        return otherSubject.getName().equals(getName())
                && otherSubject.getPhone().equals(getPhone())
                && otherSubject.getEmail().equals(getEmail())
                && otherSubject.getAddress().equals(getAddress())
                && otherSubject.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
