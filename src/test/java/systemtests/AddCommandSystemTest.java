package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DIFFICULTY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TOPIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TOPIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TOPIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalFlashcards.ALICE;
import static seedu.address.testutil.TypicalFlashcards.AMY;
import static seedu.address.testutil.TypicalFlashcards.BOB;
import static seedu.address.testutil.TypicalFlashcards.CARL;
import static seedu.address.testutil.TypicalFlashcards.HOON;
import static seedu.address.testutil.TypicalFlashcards.IDA;
import static seedu.address.testutil.TypicalFlashcards.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Address;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Email;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.FlashcardBuilder;
import seedu.address.testutil.FlashcardUtil;

public class AddCommandSystemTest extends FlashBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a flashcard without tags to a non-empty flash book, command with leading spaces
         * and trailing spaces
         * -> added
         */
        Flashcard toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + TOPIC_DESC_AMY + "  " + DIFFICULTY_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addFlashcard(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a flashcard with all fields same as another flashcard in the flash book except topic -> added */
        toAdd = new FlashcardBuilder(AMY).withTopic(VALID_TOPIC_BOB).build();
        command = AddCommand.COMMAND_WORD + TOPIC_DESC_BOB + DIFFICULTY_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a flashcard with all fields same as another flashcard in the flash book
        except difficulty and email
         * -> added
         */
        toAdd = new FlashcardBuilder(AMY).withDifficulty(VALID_DIFFICULTY_BOB).withEmail(VALID_EMAIL_BOB).build();
        command = FlashcardUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty flash book -> added */
        deleteAllFlashcards();
        assertCommandSuccess(ALICE);

        /* Case: add a flashcard with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + DIFFICULTY_DESC_BOB + ADDRESS_DESC_BOB + TOPIC_DESC_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a flashcard, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the flashcard list before adding -> added */
        showFlashcardsWithTopic(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* --------------------- Perform add operation while a flashcard card is selected ------------------------ */

        /* Case: selects first card in the flashcard list, add a flashcard -> added, card selection remains unchanged */
        selectFlashcard(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate flashcard -> rejected */
        command = FlashcardUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_FLASHCARD);

        /* Case: add a duplicate flashcard except with different difficulty -> rejected */
        toAdd = new FlashcardBuilder(HOON).withDifficulty(VALID_DIFFICULTY_BOB).build();
        command = FlashcardUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_FLASHCARD);

        /* Case: add a duplicate flashcard except with different email -> rejected */
        toAdd = new FlashcardBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
        command = FlashcardUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_FLASHCARD);

        /* Case: add a duplicate flashcard except with different address -> rejected */
        toAdd = new FlashcardBuilder(HOON).withAddress(VALID_ADDRESS_BOB).build();
        command = FlashcardUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_FLASHCARD);

        /* Case: add a duplicate flashcard except with different tags -> rejected */
        command = FlashcardUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_FLASHCARD);

        /* Case: missing topic -> rejected */
        command = AddCommand.COMMAND_WORD + DIFFICULTY_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing difficulty -> rejected */
        command = AddCommand.COMMAND_WORD + TOPIC_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + TOPIC_DESC_AMY + DIFFICULTY_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddCommand.COMMAND_WORD + TOPIC_DESC_AMY + DIFFICULTY_DESC_AMY + EMAIL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + FlashcardUtil.getFlashcardDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid topic -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_TOPIC_DESC + DIFFICULTY_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Topic.MESSAGE_CONSTRAINTS);

        /* Case: invalid difficulty -> rejected */
        command = AddCommand.COMMAND_WORD + TOPIC_DESC_AMY + INVALID_DIFFICULTY_DESC + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Difficulty.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + TOPIC_DESC_AMY + DIFFICULTY_DESC_AMY + INVALID_EMAIL_DESC
                + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + TOPIC_DESC_AMY + DIFFICULTY_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_ADDRESS_DESC;
        assertCommandFailure(command, Address.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + TOPIC_DESC_AMY + DIFFICULTY_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code FlashcardListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code FlashBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see FlashBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Flashcard toAdd) {
        assertCommandSuccess(FlashcardUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Flashcard)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Flashcard)
     */
    private void assertCommandSuccess(String command, Flashcard toAdd) {
        Model expectedModel = getModel();
        expectedModel.addFlashcard(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Flashcard)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code FlashcardListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Flashcard)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code FlashcardListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code FlashBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see FlashBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
