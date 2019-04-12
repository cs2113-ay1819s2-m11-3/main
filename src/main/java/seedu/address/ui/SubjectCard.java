package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.tag.SubjectTag;

/**
 * An UI component that displays information of a {@code Subject}.
 */
public class SubjectCard extends UiPart<Region> {

    private static final String FXML = "SubjectListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on FlashBook level 4</a>
     */

    public final SubjectTag subjectTag;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label subject;

    public SubjectCard(SubjectTag sb, int displayedIndex) {
        super(FXML);
        this.subjectTag = sb;
        id.setText(displayedIndex + ". ");
        subject.setText(subjectTag.toString());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SubjectCard)) {
            return false;
        }

        // state check
        SubjectCard card = (SubjectCard) other;
        return id.getText().equals(card.id.getText())
                && subject.equals(card.subject);
        //return subject.equals(card.subject);
    }
}