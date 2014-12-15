package de.ladis.infohm.android.validation;

import static org.hamcrest.Matchers.*;

import de.ladis.infohm.core.validation.Validator;

public class UsernameValidator extends Validator<String> {

	@Override
	public void validate(String username) {
		if (!"rest".equals(username)) {
			validateThat(username, is(notNullValue()));
			validateThat(username.length(), is(greaterThan(16)));
		}
	}

}
