package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.subject.Address;
import seedu.address.model.subject.Deadline;
import seedu.address.model.subject.Email;
import seedu.address.model.subject.Name;
import seedu.address.model.subject.Phone;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Subject}.
 */
class JsonAdaptedSubject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Subject's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String deadline;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedSubject} with the given subject details.
     */
    @JsonCreator
    public JsonAdaptedSubject(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                              @JsonProperty("email") String email, @JsonProperty("address") String address,
                              @JsonProperty("deadline") String deadline,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.deadline = deadline;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Subject} into this class for Jackson use.
     */
    public JsonAdaptedSubject(Subject source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        deadline = source.getDeadline().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted subject object into the model's {@code Subject} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted subject.
     */
    public Subject toModelType() throws IllegalValueException {
        final List<Tag> subjectTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            subjectTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));

        }
        final Deadline modelDeadline = new Deadline(deadline);


        final Set<Tag> modelTags = new HashSet<>(subjectTags);
        return new Subject(modelName, modelPhone, modelEmail, modelAddress, modelDeadline, modelTags);
    }

}