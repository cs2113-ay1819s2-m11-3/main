package seedu.address.logic.commands;

//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeadlineCommand.
 */

public class DeadlineCommandTest {

    private static final String DEADLINE_STUB = "31 December 2099";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addDeadlineUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withDeadline(DEADLINE_STUB).build();

        DeadlineCommand deadlineCommand = new DeadlineCommand(INDEX_FIRST_PERSON,
                new Deadline(editedPerson.getDeadline().value));

        String expectedMessage = String.format(DeadlineCommand.MESSAGE_ADD_DEADLINE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deadlineCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_deleteDeadlineUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withDeadline("").build();

        DeadlineCommand deadlineCommand = new DeadlineCommand(INDEX_FIRST_PERSON,
                new Deadline(editedPerson.getDeadline().toString()));

        String expectedMessage = String.format(DeadlineCommand.MESSAGE_DELETE_DEADLINE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deadlineCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withDeadline(DEADLINE_STUB).build();

        DeadlineCommand deadlineCommand = new DeadlineCommand(INDEX_FIRST_PERSON,
                new Deadline(editedPerson.getDeadline().value));

        String expectedMessage = String.format(DeadlineCommand.MESSAGE_ADD_DEADLINE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deadlineCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeadlineCommand deadlineCommand = new DeadlineCommand(outOfBoundIndex, VALID_DEADLINE_BOB);

        assertCommandFailure(deadlineCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        //ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeadlineCommand deadlineCommand = new DeadlineCommand(outOfBoundIndex, VALID_DEADLINE_BOB);

        assertCommandFailure(deadlineCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withDeadline(DEADLINE_STUB).build();
        DeadlineCommand deadlineCommand = new DeadlineCommand(INDEX_FIRST_PERSON, new Deadline(DEADLINE_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        //deadline -> first person deadline changed
        deadlineCommand.execute(model, commandHistory);

        //undo -> reverts address book back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> same first person modified again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeadlineCommand deadlineCommand = new DeadlineCommand(outOfBoundIndex, new Deadline(""));

        //execution failed -> address book state not added into model
        assertCommandFailure(deadlineCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Person#deadline} from a filtered list.
     * 2. Undo the modification.
     * The unfiltered lsit should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */

    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeadlineCommand deadlineCommand = new DeadlineCommand(INDEX_FIRST_PERSON, new Deadline(DEADLINE_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withDeadline(DEADLINE_STUB).build();
        expectedModel.setPerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        //deadline -> modifies second person in unfiltered person list/first person in filtered person list
        deadlineCommand.execute(model, commandHistory);

        //undo -> reverts address book back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> modifies same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

    }

}
