package seedu.address.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Flashcard}'s {@code Topic} matches any of the keywords given.
 */
public class TopicContainsKeywordsPredicate implements Predicate<Flashcard> {
    private final List<String> keywords;

    public TopicContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(flashcard.getTopic().fullTopic, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TopicContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TopicContainsKeywordsPredicate) other).keywords)); // state check
    }

}
