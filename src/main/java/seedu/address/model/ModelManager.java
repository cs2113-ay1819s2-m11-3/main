package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.exceptions.FlashcardNotFoundException;

/**
 * Represents the in-memory model of the flash book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedFlashBook versionedFlashBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Flashcard> filteredFlashcards;
    private final SimpleObjectProperty<Flashcard> selectedFlashcard = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given flashBook and userPrefs.
     */
    public ModelManager(ReadOnlyFlashBook flashBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(flashBook, userPrefs);

        logger.fine("Initializing with flash book: " + flashBook + " and user prefs " + userPrefs);

        versionedFlashBook = new VersionedFlashBook(flashBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashcards = new FilteredList<>(versionedFlashBook.getFlashcardList());
        filteredFlashcards.addListener(this::ensureSelectedFlashcardIsValid);
    }

    public ModelManager() {
        this(new FlashBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getFlashBookFilePath() {
        return userPrefs.getFlashBookFilePath();
    }

    @Override
    public void setFlashBookFilePath(Path flashBookFilePath) {
        requireNonNull(flashBookFilePath);
        userPrefs.setFlashBookFilePath(flashBookFilePath);
    }

    //=========== FlashBook ================================================================================

    @Override
    public void setFlashBook(ReadOnlyFlashBook flashBook) {
        versionedFlashBook.resetData(flashBook);
    }

    @Override
    public ReadOnlyFlashBook getFlashBook() {
        return versionedFlashBook;
    }

    @Override
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return versionedFlashBook.hasFlashcard(flashcard);
    }

    @Override
    public void deleteFlashcard(Flashcard target) {
        versionedFlashBook.removeFlashcard(target);
    }

    @Override
    public void addFlashcard(Flashcard flashcard) {
        versionedFlashBook.addFlashcard(flashcard);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        versionedFlashBook.setFlashcard(target, editedFlashcard);
    }

    //=========== Filtered Flashcard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} backed by the internal list of
     * {@code versionedFlashBook}
     */
    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return filteredFlashcards;
    }

    @Override
    public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
        requireNonNull(predicate);
        filteredFlashcards.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoFlashBook() {
        return versionedFlashBook.canUndo();
    }

    @Override
    public boolean canRedoFlashBook() {
        return versionedFlashBook.canRedo();
    }

    @Override
    public void undoFlashBook() {
        versionedFlashBook.undo();
    }

    @Override
    public void redoFlashBook() {
        versionedFlashBook.redo();
    }

    @Override
    public void commitFlashBook() {
        versionedFlashBook.commit();
    }

    //=========== Selected flashcard ===========================================================================

    @Override
    public ReadOnlyProperty<Flashcard> selectedFlashcardProperty() {
        return selectedFlashcard;
    }

    @Override
    public Flashcard getSelectedFlashcard() {
        return selectedFlashcard.getValue();
    }

    @Override
    public void setSelectedFlashcard(Flashcard flashcard) {
        if (flashcard != null && !filteredFlashcards.contains(flashcard)) {
            throw new FlashcardNotFoundException();
        }
        selectedFlashcard.setValue(flashcard);
    }

    /**
     * Ensures {@code selectedFlashcard} is a valid flashcard in {@code filteredFlashcards}.
     */
    private void ensureSelectedFlashcardIsValid(ListChangeListener.Change<? extends Flashcard> change) {
        while (change.next()) {
            if (selectedFlashcard.getValue() == null) {
                // null is always a valid selected flashcard, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedFlashcardReplaced = change.wasReplaced()
                    && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedFlashcard.getValue());
            if (wasSelectedFlashcardReplaced) {
                // Update selectedFlashcard to its new value.
                int index = change.getRemoved().indexOf(selectedFlashcard.getValue());
                selectedFlashcard.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedFlashcardRemoved = change.getRemoved().stream()
                    .anyMatch(removedFlashcard -> selectedFlashcard.getValue().isSameFlashcard(removedFlashcard));
            if (wasSelectedFlashcardRemoved) {
                // Select the flashcard that came before it in the list,
                // or clear the selection if there is no such flashcard.
                selectedFlashcard.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedFlashBook.equals(other.versionedFlashBook)
                && userPrefs.equals(other.userPrefs)
                && filteredFlashcards.equals(other.filteredFlashcards)
                && Objects.equals(selectedFlashcard.get(), other.selectedFlashcard.get());
    }

}
