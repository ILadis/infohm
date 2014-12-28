package de.ladis.infohm.android.animation.events;

import static android.view.View.*;
import butterknife.InjectView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import de.ladis.infohm.R;
import de.ladis.infohm.android.animation.BaseAnimator;

public class EventsAnimator extends BaseAnimator {

	@InjectView(R.id.fragment_events_list)
	protected RecyclerView recyclerView;

	@InjectView(R.id.fragment_events_no_content)
	protected View noContentView;

	private Boolean animate;

	public EventsAnimator(Fragment fragment) {
		super(fragment);
	}

	public void animateOneShots(boolean animate) {
		this.animate = animate;
	}

	public void animateItems() {
		if (animate) {
			animate = false;
			recyclerView.post(new Runnable() {

				@Override
				public void run() {
					int count = recyclerView.getChildCount();

					for (int i = 0; i < count; i++) {
						View child = recyclerView.getChildAt(i);

						ViewCompat.setTranslationY(child, 100);
						ViewCompat.setAlpha(child, 0);

						ViewCompat.animate(child)
								.translationY(0)
								.alpha(1)
								.setStartDelay(400 + i * 120)
								.setDuration(300)
								.start();
					}
				}

			});
		}
	}

	public void animateNoContents(boolean show) {
		if (show) {
			if (noContentView.getVisibility() != VISIBLE) {
				ViewCompat.setAlpha(noContentView, 0);
				ViewCompat.animate(noContentView)
						.alpha(1)
						.start();
			}

			noContentView.setVisibility(VISIBLE);
		} else {
			noContentView.setVisibility(GONE);
		}
	}

}
