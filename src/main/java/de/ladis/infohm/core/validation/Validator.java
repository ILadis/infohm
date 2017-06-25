package de.ladis.infohm.core.validation;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public abstract class Validator<T> {

	public static <T> void validateThat(T actual, Matcher<? super T> matcher) {
		if (!matcher.matches(actual)) {
			Description description = new StringDescription()
				.appendText("\nExpected: ")
				.appendDescriptionOf(matcher)
				.appendText("\n     got: ")
				.appendValue(actual)
				.appendText("\n");

			throw new AssertionError(description.toString());
		}
	}

	public boolean valid(T value) {
		try {
			validate(value);

			return true;
		} catch (Throwable e) {
			e.printStackTrace();

			return false;
		}
	}

	protected abstract void validate(T value);

}
