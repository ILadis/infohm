package de.ladis.infohm.android.animation.events;

import static android.view.View.*;
import static android.view.ViewAnimationUtils.*;
import static android.widget.RelativeLayout.*;
import butterknife.InjectView;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import de.ladis.infohm.R;
import de.ladis.infohm.android.animation.BaseAnimator;
import de.ladis.infohm.android.widget.SlidingTabLayout;

public class EventsPagerAnimator extends BaseAnimator {

	@InjectView(R.id.fragment_events_pager_tabs)
	protected SlidingTabLayout tabView;

	@InjectView(R.id.fragment_events_pager)
	protected ViewPager pagerView;

	@InjectView(R.id.fragment_events_pager_action)
	protected View actionView;

	@InjectView(R.id.fragment_events_pager_action_overlay)
	protected View overlayView;

	private Boolean animateTabs, animateButton;

	public EventsPagerAnimator(Fragment fragment) {
		super(fragment);
	}

	public void animateOneShots(boolean animate) {
		this.animateTabs = animate;
		this.animateButton = animate;
	}

	public void animateTabAppearance() {
		if (animateTabs) {
			animateTabs = false;

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

	public void animateActionButton() {
		int count = pagerView.getAdapter().getCount();

		RelativeLayout.LayoutParams params = (LayoutParams) actionView.getLayoutParams();

		if (count > 1) {
			params.addRule(ALIGN_PARENT_BOTTOM);
		} else {
			params.removeRule(ALIGN_PARENT_BOTTOM);
		}

		actionView.setLayoutParams(params);
		actionView.requestLayout();

		if (animateButton) {
			animateButton = false;

			if (count > 1) {
				ViewCompat.setTranslationY(actionView, 200 * getDisplayMetrics().density);

				ViewCompat.animate(actionView)
						.translationY(0)
						.setDuration(800)
						.start();
			}
		}
	}

	public void animateActionTransition(AnimatorListener listener) {
		if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
			int cx = (actionView.getLeft() + actionView.getRight()) / 2;
			int cy = (actionView.getTop() + actionView.getBottom()) / 2;
			int finalRadius = Math.max(overlayView.getWidth(), overlayView.getHeight());

			Animator animator = createCircularReveal(overlayView, cx, cy, 0, finalRadius);
			animator.setDuration(130);
			animator.addListener(listener);

			overlayView.setVisibility(VISIBLE);
			animator.start();
		} else {
			listener.onAnimationEnd(null);
		}
	}

}
