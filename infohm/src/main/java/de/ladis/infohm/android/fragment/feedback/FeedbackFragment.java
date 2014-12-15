package de.ladis.infohm.android.fragment.feedback;

import butterknife.InjectView;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.controller.FeedbackController;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.android.validation.FeedbackValidator;
import de.ladis.infohm.android.validation.ViewValidator;
import de.ladis.infohm.android.widget.EditText;
import de.ladis.infohm.core.validation.Validator;

public class FeedbackFragment extends BaseFragment {

	private FeedbackController controller;

	@InjectView(R.id.fragment_feedback_subject)
	protected EditText subjectView;

	@InjectView(R.id.fragment_feedback_message)
	protected EditText messageView;

	private Validator<TextView> messageValidator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		controller = (FeedbackController) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_feedback, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		messageView.setErrorView(view.findViewById(R.id.fragment_feedback_message_error));

		messageValidator = new ViewValidator(new FeedbackValidator());

		if (savedInstanceState != null) {
			subjectView.setText(savedInstanceState.getString("subject"));
			messageView.setText(savedInstanceState.getString("message"));

			validateForm();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_feedback, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.fragment_feedback_menu_submit:
			submitFeedback();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private boolean validateForm() {
		boolean valid = true;

		messageView.setError(null);
		if (!messageValidator.valid(messageView)) {
			messageView.setError(getString(R.string.fragment_feedback_message_invalid));
			valid = false;
		}

		return valid;
	}

	private void submitFeedback() {
		String subject = subjectView.getText().toString();
		String message = messageView.getText().toString();

		if (validateForm()) {
			if (subject == null || subject.isEmpty()) {
				subject = getString(R.string.fragment_feedback_subject_default);
			}

			controller.submit(subject, message);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString("subject", subjectView.getText().toString());
		outState.putString("message", messageView.getText().toString());
	}

}
