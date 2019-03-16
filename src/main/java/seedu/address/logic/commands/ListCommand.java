package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUBJECTS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all subjects in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";

    public static final String MESSAGE_SUCCESS = "Listed all subjects";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredSubjectList(PREDICATE_SHOW_ALL_SUBJECTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
