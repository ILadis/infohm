package de.ladis.infohm.core.validation.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ContainsAtLeastMatcher extends TypeSafeMatcher<String> {

	public static ContainsAtLeastMatcher containsAtLeastOneOf(String... characters) {
		return new ContainsAtLeastMatcher(characters);
	}

	private final String[] characters;

	private ContainsAtLeastMatcher(String[] characters) {
		this.characters = characters;
	}

	@Override
	public void describeTo(Description description) {
	}

	@Override
	public boolean matchesSafely(String actual) {
		for (String character : characters) {
			if (actual.indexOf(character) >= 0) {
				return true;
			}
		}

		return false;
	}

}
