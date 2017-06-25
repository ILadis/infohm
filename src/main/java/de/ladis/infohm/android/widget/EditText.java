package de.ladis.infohm.android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class EditText extends android.widget.EditText {

	private android.widget.TextView errorView;

	public EditText(Context context) {
		super(context);
	}

	public EditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setErrorView(View view) {
		errorView = (android.widget.TextView) view;
	}

	@Override
	public void setError(CharSequence error) {
		setActivated(error != null);

		if (errorView != null) {
			errorView.setText(error);
			errorView.setVisibility(error != null ? VISIBLE : INVISIBLE);
		}
	}

	@Override
	public void setError(CharSequence error, Drawable icon) {
		setError(error);
	}

}
