package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;

/**
 * Selects a flashcard identified using it's displayed index from the flash book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the flashcard identified by the index number used in the displayed flashcard list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_FLASHCARD_SUCCESS = "Selected Flashcard: %1$s";

    private final Index targetIndex;

    private Flashcard selectedFlashcard;


    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();
        ObservableList<Flashcard> updatedFlashcardList = model.getUpdatedFlashcardList();

        if (model.getSelectedSubject() == null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
            }
            model.setSelectedSubject(null);
            selectedFlashcard = lastShownList.get(targetIndex.getZeroBased());
            model.setSelectedFlashcard(selectedFlashcard);
        } else {
            if (targetIndex.getZeroBased() >= updatedFlashcardList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
            }
            selectedFlashcard = updatedFlashcardList.get(targetIndex.getZeroBased());
            model.setSelectedSubject(selectedFlashcard.getSubject());
            model.setSelectedFlashcard(selectedFlashcard);
        }


        return new CommandResult(String.format(MESSAGE_SELECT_FLASHCARD_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
