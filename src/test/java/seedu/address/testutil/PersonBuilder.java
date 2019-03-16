package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.subject.Address;
import seedu.address.model.subject.Deadline;
import seedu.address.model.subject.Email;
import seedu.address.model.subject.Name;
import seedu.address.model.subject.Phone;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Subject objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DEADLINE = "";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Deadline deadline;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        deadline = new Deadline(DEFAULT_DEADLINE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code subjectToCopy}.
     */
    public PersonBuilder(Subject subjectToCopy) {
        name = subjectToCopy.getName();
        phone = subjectToCopy.getPhone();
        email = subjectToCopy.getEmail();
        address = subjectToCopy.getAddress();
        deadline = subjectToCopy.getDeadline();
        tags = new HashSet<>(subjectToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Subject} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Subject} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Subject} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Subject} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Subject} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Subject} that we are building.
     */

    public PersonBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    public Subject build() {
        return new Subject(name, phone, email, address, deadline, tags);
    }

}
