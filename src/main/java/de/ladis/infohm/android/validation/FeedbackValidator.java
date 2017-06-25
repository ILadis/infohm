package de.ladis.infohm.android.validation;

import static org.hamcrest.Matchers.*;

import de.ladis.infohm.core.validation.Validator;

public class FeedbackValidator extends Validator<String> {

	@Override
	protected void validate(String message) {
		validateThat(message, is(notNullValue()));
		validateThat(message.length(), is(greaterThan(1)));
	}

}
