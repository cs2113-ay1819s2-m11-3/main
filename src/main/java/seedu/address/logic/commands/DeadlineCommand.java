package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Changes the deadline or exam of an existing subject in the address book/flashcards.
 */

public class DeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";
    public static final String COMMAND_ALIAS = "exam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the deadline/exam of a subject identified "
            + "by the index number used in the last person listing."
            + "Existing deadline will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DEADLINE + "[DEADLINE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DEADLINE + "04 April 2028";

    public static final String MESSAGE_ADD_DEADLINE_SUCCESS = "Added deadline to Person: %1$s";
    public static final String MESSAGE_DELETE_DEADLINE_SUCCESS = "Removed deadline from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This deadline clashes with another in the address book.";

    private final Index index;
    private final Deadline deadline;

    /**
     * @param index of the person in the filtered person list to edit the deadline
     * @param deadline of the person to be updated to
     */

    public DeadlineCommand(Index index, Deadline deadline) {
        //requireAllNonNull(index, deadline);
        requireNonNull(index);
        requireNonNull(deadline);

        this.index = index;
        this.deadline = deadline;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), deadline, personToEdit.getTags());

        try {
            model.setPerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        //model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether the deadline is added to or removed from
     * {@code personToEdit}.
     */

    private String generateSuccessMessage(Person personToEdit) {
        String message = !deadline.value.isEmpty() ? MESSAGE_ADD_DEADLINE_SUCCESS : MESSAGE_DELETE_DEADLINE_SUCCESS;
        return String.format(message, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof DeadlineCommand)) {
            return false;
        }

        //state check
        DeadlineCommand e = (DeadlineCommand) other;
        return index.equals(e.index)
                && deadline.equals(e.deadline);
    }
}
