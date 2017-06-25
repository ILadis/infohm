package de.ladis.infohm.android.animation;

import butterknife.ButterKnife;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;

public class BaseAnimator {

	private final View view;
	private final DisplayMetrics metrics;

	public BaseAnimator(Fragment fragment) {
		this(fragment.getView());
	}

	public BaseAnimator(View view) {
		this.view = view;
		this.metrics = view.getResources().getDisplayMetrics();

		ButterKnife.inject(this, view);
	}

	public View getView() {
		return view;
	}

	public DisplayMetrics getDisplayMetrics() {
		return metrics;
	}

}
