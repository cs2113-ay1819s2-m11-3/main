/*
package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TOPIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DIFFICULTY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TOPIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TOPIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static seedu.address.testutil.TypicalFlashcards.AMY;
import static seedu.address.testutil.TypicalFlashcards.BOB;
import static seedu.address.testutil.TypicalFlashcards.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
<<<<<<< HEAD
import seedu.address.model.flashcard.Address;
import seedu.address.model.flashcard.Email;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.flashcard.Phone;
=======
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Topic;
>>>>>>> a43dedf4b0c227525d0fc6ec76c8d584d3c3ba60
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.tag.SubjectTag;
import seedu.address.testutil.FlashcardBuilder;
import seedu.address.testutil.FlashcardUtil;

public class EditCommandSystemTest extends FlashBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        */
/* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- *//*


        */
/* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         *//*

        Index index = INDEX_FIRST_FLASHCARD;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + TOPIC_DESC_BOB + "  "
                + DIFFICULTY_DESC_BOB + " " + EMAIL_DESC_BOB + "  " + CONTENT_DESC_BOB + " " + TAG_DESC_HUSBAND + " ";
        Flashcard editedSubject = new FlashcardBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedSubject);

        */
/* Case: undo editing the last flashcard in the list -> last flashcard restored *//*

        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        */
/* Case: redo editing the last flashcard in the list -> last flashcard edited again *//*

        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setFlashcard(getModel().getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased()),
        editedSubject);
        assertCommandSuccess(command, model, expectedResultMessage);

        */
/* Case: edit a flashcard with new values same as existing values -> edited *//*

      command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB
      + EMAIL_DESC_BOB
                + CONTENT_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        */
/* Case: edit a flashcard with new values same as another flashcard's values but with different topic -> edited *//*

        assertTrue(getModel().getFlashBook().getFlashcardList().contains(BOB));
        index = INDEX_SECOND_FLASHCARD;
        assertNotEquals(getModel().getFilteredFlashcardList().get(index.getZeroBased()), BOB);
      command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TOPIC_DESC_AMY + DIFFICULTY_DESC_BOB
      + EMAIL_DESC_BOB
                + CONTENT_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedSubject = new FlashcardBuilder(BOB).withTopic(VALID_TOPIC_AMY).build();
        assertCommandSuccess(command, index, editedSubject);

        */
/* Case: edit a flashcard with new values same as another flashcard's values but with different difficulty
         * -> edited
         *//*

        index = INDEX_SECOND_FLASHCARD;
      command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TOPIC_DESC_BOB + DIFFICULTY_DESC_AMY
      + EMAIL_DESC_AMY
                + CONTENT_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedSubject =
        new FlashcardBuilder(BOB).withDifficulty(VALID_DIFFICULTY_AMY).withEmail(VALID_EMAIL_AMY).build();
        assertCommandSuccess(command, index, editedSubject);

        */
/* Case: clear tags -> cleared *//*

        index = INDEX_FIRST_FLASHCARD;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_SUBJECT.getPrefix();
        Flashcard subjectToEdit = getModel().getFilteredFlashcardList().get(index.getZeroBased());
        editedSubject = new FlashcardBuilder(subjectToEdit).withTags().build();
        assertCommandSuccess(command, index, editedSubject);

        */
/* ------------------ Performing edit operation while a filtered list is being shown ------------------------ *//*


        */
/* Case: filtered flashcard list, edit index within bounds of flash book and flashcard list -> edited *//*

        showFlashcardsWithTopic(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_FLASHCARD;
        assertTrue(index.getZeroBased() < getModel().getFilteredFlashcardList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + TOPIC_DESC_BOB;
        subjectToEdit = getModel().getFilteredFlashcardList().get(index.getZeroBased());
        editedSubject = new FlashcardBuilder(subjectToEdit).withTopic(VALID_TOPIC_BOB).build();
        assertCommandSuccess(command, index, editedSubject);

        */
/* Case: filtered flashcard list, edit index within bounds of flash book but out of bounds of flashcard list
         * -> rejected
         *//*

        showFlashcardsWithTopic(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getFlashBook().getFlashcardList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + TOPIC_DESC_BOB,
                Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);

        */
/* ------------------ Performing edit operation while a flashcard card is selected ----------------------- *//*


        */
/* Case: selects first card in the flashcard list, edit a flashcard -> edited, card selection remains unchanged but
         * browser url changes
         *//*

        showAllFlashcards();
        index = INDEX_FIRST_FLASHCARD;
        selectFlashcard(index);
      command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TOPIC_DESC_AMY + DIFFICULTY_DESC_AMY
      + EMAIL_DESC_AMY
                + CONTENT_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new flashcard's topic
        assertCommandSuccess(command, index, AMY, index);

        */
/* ------------------------------ Performing invalid edit operation ---------------------------------- *//*


        */
/* Case: invalid index (0) -> rejected *//*

        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + TOPIC_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        */
/* Case: invalid index (-1) -> rejected *//*

        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + TOPIC_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        */
/* Case: invalid index (size + 1) -> rejected *//*

        invalidIndex = getModel().getFilteredFlashcardList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + TOPIC_DESC_BOB,
                Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);

        */
/* Case: missing index -> rejected *//*

        assertCommandFailure(EditCommand.COMMAND_WORD + TOPIC_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        */
/* Case: missing all fields -> rejected *//*

        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        */
/* Case: invalid topic -> rejected *//*

        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased()
<<<<<<< HEAD
                + INVALID_NAME_DESC, Topic.MESSAGE_CONSTRAINTS);
=======
                + INVALID_TOPIC_DESC, Topic.MESSAGE_CONSTRAINTS);
>>>>>>> a43dedf4b0c227525d0fc6ec76c8d584d3c3ba60

        */
/* Case: invalid difficulty -> rejected *//*

        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased()
                + INVALID_DIFFICULTY_DESC, Difficulty.MESSAGE_CONSTRAINTS);

        */
/* Case: invalid content -> rejected *//*

        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased()
                + INVALID_CONTENT_DESC, Content.MESSAGE_CONSTRAINTS);

        */
/* Case: invalid tag -> rejected *//*

        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased()
                + INVALID_TAG_DESC, SubjectTag.MESSAGE_CONSTRAINTS);

        */
/* Case: edit a flashcard with new values same as another flashcard's values -> rejected *//*

        executeCommand(FlashcardUtil.getAddCommand(BOB));
        assertTrue(getModel().getFlashBook().getFlashcardList().contains(BOB));
        index = INDEX_FIRST_FLASHCARD;
        assertFalse(getModel().getFilteredFlashcardList().get(index.getZeroBased()).equals(BOB));
      command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB
      + EMAIL_DESC_BOB
                + CONTENT_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);

        */
/* Case: edit a flashcard with new values same as another flashcard's values but with different tags
        -> rejected *//*

      command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB
      + EMAIL_DESC_BOB
                + CONTENT_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);

        */
/* Case: edit a flashcard with new values same as another flashcard's values but with different content
        -> rejected *//*

      command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TOPIC_DESC_BOB + DIFFICULTY_DESC_BOB
      + EMAIL_DESC_BOB
                + CONTENT_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);

        */
/* Case: edit a flashcard with new values same as another flashcard's values but with different difficulty
-> rejected *//*

      command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TOPIC_DESC_BOB + DIFFICULTY_DESC_AMY
      + EMAIL_DESC_BOB
                + CONTENT_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);

        */
/*
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Flashcard, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, SubjectTag, Index)
     *//*

    private void assertCommandSuccess(String command, Index toEdit, Flashcard editedSubject) {
        assertCommandSuccess(command, toEdit, editedSubject, null);
    }

    */
/*
 * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the flashcard at index {@code toEdit} being
     * updated to values specified {@code editedSubject}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     *//*

    private void assertCommandSuccess(String command, Index toEdit, Flashcard editedSubject,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setFlashcard(expectedModel.getFilteredFlashcardList().get(toEdit.getZeroBased()), editedSubject);
        expectedModel.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedSubject), expectedSelectedCardIndex);
    }

    */
/*
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     *//*

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    */
/*
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code FlashBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     *//*

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    */
/*
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code FlashBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     *//*

    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
*/
