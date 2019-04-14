package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashcards.getTypicalFlashBook;
import static seedu.address.testutil.TypicalSubjects.getTypicalSubjectBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.TopicContainsDifficultyPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalSubjectBook(), getTypicalFlashBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSubjectBook(), getTypicalFlashBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        TopicContainsDifficultyPredicate firstPredicate =
                new TopicContainsDifficultyPredicate(Collections.singletonList("1"));
        TopicContainsDifficultyPredicate secondPredicate =
                new TopicContainsDifficultyPredicate(Collections.singletonList("2"));

        SortCommand sortFirstCommand = new SortCommand(firstPredicate, firstPredicate.toString().split("//s+"));
        SortCommand sortSecondCommand = new SortCommand(secondPredicate, secondPredicate.toString().split("//s+"));

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        SortCommand sortFirstCommandCopy = new SortCommand(firstPredicate, firstPredicate.toString().split("//s+"));
        assertTrue(sortFirstCommand.equals(sortFirstCommandCopy));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different flashcard -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noFlashcardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 0);
        TopicContainsDifficultyPredicate predicate = preparePredicate(" ");
        SortCommand command = new SortCommand(predicate, predicate.toString().split("//s+"));
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashcardList());
    }

    /**
     * Parses {@code userInput} into a {@code TopicContainsKeywordsPredicate}.
     */
    private TopicContainsDifficultyPredicate preparePredicate(String userInput) {
        return new TopicContainsDifficultyPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
