package de.ladis.infohm.android.validation;

import android.widget.TextView;
import de.ladis.infohm.core.validation.Validator;

public class ViewValidator extends Validator<TextView> {

	private Validator<String> validator;

	public ViewValidator(Validator<String> validator) {
		this.validator = validator;
	}

	@Override
	public boolean valid(TextView view) {
		return validator.valid(view.getText().toString());
	}

	@Override
	protected void validate(TextView view) { }

}
