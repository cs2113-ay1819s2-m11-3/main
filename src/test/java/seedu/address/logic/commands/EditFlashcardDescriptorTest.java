package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_CHINESE;

import org.junit.Test;

import seedu.address.testutil.EditFlashcardDescriptorBuilder;

public class EditFlashcardDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditFlashcardDescriptor descriptorWithSameValues =
                new EditCommand.EditFlashcardDescriptor(DESC_ENGLISH);
        assertTrue(DESC_ENGLISH.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ENGLISH.equals(DESC_ENGLISH));

        // null -> returns false
        assertFalse(DESC_ENGLISH.equals(null));

        // different types -> returns false
        assertFalse(DESC_ENGLISH.equals(5));

        // different values -> returns false
        assertFalse(DESC_ENGLISH.equals(DESC_CHINESE));

        // different topic -> returns false
        EditCommand.EditFlashcardDescriptor editedEnglish = new EditFlashcardDescriptorBuilder(DESC_ENGLISH)
            .withTopic(VALID_TOPIC_CHINESE).build();
        assertFalse(DESC_ENGLISH.equals(editedEnglish));

        // different difficulty -> returns false
        editedEnglish = new EditFlashcardDescriptorBuilder(DESC_ENGLISH)
                .withDifficulty(VALID_DIFFICULTY_CHINESE).build();
        assertFalse(DESC_ENGLISH.equals(editedEnglish));

        // different content -> returns false
        editedEnglish = new EditFlashcardDescriptorBuilder(DESC_ENGLISH).withContent(VALID_CONTENT_CHINESE).build();
        assertFalse(DESC_ENGLISH.equals(editedEnglish));

        // different tags -> returns false
        editedEnglish = new EditFlashcardDescriptorBuilder(DESC_ENGLISH).withTags(VALID_TAG_ENGLISH).build();
        assertFalse(DESC_ENGLISH.equals(editedEnglish));
    }
}
