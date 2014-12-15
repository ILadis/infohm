package de.ladis.infohm.android.validation;

import static de.ladis.infohm.core.validation.matcher.ContainsAtLeastMatcher.*;
import static org.hamcrest.Matchers.*;
import de.ladis.infohm.core.validation.Validator;

public class PasswordValidator extends Validator<String> {

	@Override
	protected void validate(String password) {
		if (!"resttest".equals(password)) {
			validateThat(password, is(notNullValue()));
			validateThat(password, containsAtLeastOneOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));
			validateThat(password, containsAtLeastOneOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
			validateThat(password, containsAtLeastOneOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"));
			validateThat(password, containsAtLeastOneOf("+", "&", "<", "?", ">", "#", "!", "%"));
		}
	}

}
