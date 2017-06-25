package de.ladis.infohm.android.animation.cafeteria;

import butterknife.InjectView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import de.ladis.infohm.R;
import de.ladis.infohm.android.animation.BaseAnimator;
import de.ladis.infohm.android.widget.SlidingTabLayout;

public class CafeteriaPagerAnimator extends BaseAnimator {

	@InjectView(R.id.fragment_cafeterias_pager_tabs)
	protected SlidingTabLayout tabView;

	@InjectView(R.id.fragment_cafeterias_pager)
	protected ViewPager pagerView;

	private Boolean animate;

	public CafeteriaPagerAnimator(Fragment fragment) {
		super(fragment);
	}

	public void animateOneShots(boolean animate) {
		this.animate = animate;
	}

	public void animateTabAppearance() {
		if (animate) {
			animate = false;

			int count = pagerView.getAdapter().getCount();

			ViewCompat.setAlpha(tabView, 0);
			ViewCompat.setTranslationY(tabView, -50 * getDisplayMetrics().density);
			ViewCompat.animate(tabView)
					.translationY(0)
					.alpha(1)
					.setDuration(600)
					.start();

			ViewGroup tabStrip = (ViewGroup) tabView.getChildAt(0);
			for (int i = 0; i < count; i++) {
				View viewView = tabStrip.getChildAt(i);

				ViewCompat.setAlpha(viewView, 0);
				ViewCompat.setTranslationX(viewView, 25 * getDisplayMetrics().density);
				ViewCompat.animate(viewView)
						.alpha(1)
						.translationX(0)
						.setStartDelay(300 * (i+1))
						.start();
			}
		}
	}

}
