package de.ladis.infohm.android.validation;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Matchers.*;

import de.ladis.infohm.core.validation.Validator;

public class UsernameValidator extends Validator<String> {

	@Override
	public void validate(String username) {
		if (!"rest".equals(username)) {
			validateThat(username, is(notNull()));
			validateThat(username.length(), is(gt(16)));
		}
	}

}
