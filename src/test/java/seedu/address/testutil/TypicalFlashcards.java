package seedu.address.testutil;
//
//import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.FlashBook;
import seedu.address.model.flashcard.Flashcard;

/**
 * A utility class containing a list of {@code Flashcard} objects to be used in tests.
 */
public class TypicalFlashcards {

    public static final Flashcard ENGLISH = new FlashcardBuilder().withTopic("Brown Fox")
            .withContent("The quick brown fox jumps over the lazy dog")
            .withDifficulty("1")
            .withTags("english").build();
    public static final Flashcard CHINESE = new FlashcardBuilder().withTopic("Ni hao ma")
            .withContent("Hao")
            .withDifficulty("2")
            .withTags("chinese").build();
    public static final Flashcard MATH = new FlashcardBuilder().withTopic("Pythagoras Theorem")
            .withDifficulty("1")
            .withContent("a^2 + b^2 = c^2")
            .withTags("math").build();
    public static final Flashcard CHEMISTRY = new FlashcardBuilder().withTopic("Acids")
            .withDifficulty("1")
            .withContent("This is acidic.")
            .withTags("chemistry").build();
    public static final Flashcard PHYSICS = new FlashcardBuilder().withTopic("Force")
            .withDifficulty("1")
            .withContent("F = ma")
            .withTags("physics").build();
    public static final Flashcard GEOGRAPHY = new FlashcardBuilder().withTopic("Human Geography")
            .withDifficulty("2")
            .withContent("Food")
            .withTags("geography").build();
    public static final Flashcard HISTORY = new FlashcardBuilder().withTopic("Singapore")
            .withDifficulty("1")
            .withContent("Gained independence in 1965")
            .withTags("english")
            .withDeadline("21 August 2022").build();

    // Manually added
    public static final Flashcard CS2101 = new FlashcardBuilder().withTopic("Communication Skills")
            .withDifficulty("2")
            .withContent("People have different styles of communication")
            .withTags("cs2101")
            .build();
    public static final Flashcard CS2113T = new FlashcardBuilder().withTopic("Inheritance")
            .withDifficulty("1")
            .withContent("Child class inherits parent class")
            .withTags("cs2113T")
            .build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalFlashcards() {} // prevents instantiation

    /**
     * Returns an {@code FlashBook} with all the typical subjects.
     */
    public static FlashBook getTypicalFlashBook() {
        FlashBook fb = new FlashBook();
        for (Flashcard flashcard : getTypicalFlashcards()) {
            fb.addFlashcard(flashcard);
        }
        return fb;
    }

    public static List<Flashcard> getTypicalFlashcards() {
        return new ArrayList<>(Arrays.asList(ENGLISH, CHINESE, MATH, CHEMISTRY, PHYSICS, GEOGRAPHY, HISTORY));
    }
}
