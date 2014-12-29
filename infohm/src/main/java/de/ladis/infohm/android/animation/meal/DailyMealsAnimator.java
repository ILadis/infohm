package de.ladis.infohm.android.animation.meal;

import static android.view.View.*;
import butterknife.InjectView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.View;
import de.ladis.infohm.R;
import de.ladis.infohm.android.animation.BaseAnimator;

public class DailyMealsAnimator extends BaseAnimator {

	@InjectView(R.id.fragment_daily_meals_card)
	protected View cardView;

	@InjectView(R.id.fragment_daily_meals_no_content)
	protected View noContentView;

	private Boolean animate;

	public DailyMealsAnimator(Fragment fragment) {
		super(fragment);
	}

	public void animateOneShots(boolean animate) {
		this.animate = animate;
	}

	public void animateOffersAppearance() {
		if (animate) {
			animate = false;

			ViewCompat.setTranslationY(cardView, 100);
			ViewCompat.setAlpha(cardView, 0);

			ViewCompat.animate(cardView)
					.translationY(0)
					.alpha(1)
					.setStartDelay(400)
					.setDuration(300)
					.start();
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

			cardView.setVisibility(GONE);
			noContentView.setVisibility(VISIBLE);
		} else {
			cardView.setVisibility(VISIBLE);
			noContentView.setVisibility(GONE);
		}
	}

}
