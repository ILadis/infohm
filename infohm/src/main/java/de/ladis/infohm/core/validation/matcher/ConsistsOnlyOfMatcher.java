package de.ladis.infohm.core.validation.matcher;

import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import de.ladis.infohm.util.Arrays;

public class ConsistsOnlyOfMatcher extends TypeSafeMatcher<String> {

	public static ConsistsOnlyOfMatcher consistsOnlyOf(String... characters) {
		return new ConsistsOnlyOfMatcher(characters);
	}

	private final String[] characters;

	private ConsistsOnlyOfMatcher(String[] characters) {
		this.characters = characters;
	}

	@Override
	public void describeTo(Description description) {
	}

	@Override
	public boolean matchesSafely(String actual) {
		return Pattern.matches("[" + Arrays.glue(characters) +"]+", actual);
	}

}
