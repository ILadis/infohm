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

public class FeedbackFragment extends BaseFragment {

	private FeedbackController controller;

	@InjectView(R.id.fragment_feedback_subject)
	protected TextView subjectView;

	@InjectView(R.id.fragment_feedback_message)
	protected TextView messageView;

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
		return false;
	}

	private void submitFeedback() {
		String subject = subjectView.getText().toString();
		String message = messageView.getText().toString();

		if (validateForm()) {
			controller.submit(subject, message);
		}
	}

}
