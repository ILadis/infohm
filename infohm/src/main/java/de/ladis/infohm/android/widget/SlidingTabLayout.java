package de.ladis.infohm.android.widget;

import de.ladis.infohm.R;
import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class SlidingTabLayout extends HorizontalScrollView {

	private ViewPager viewPager;
	private ViewPager.OnPageChangeListener pageChangeListener;

	private final int titleOffset;
	private final SlidingTabStrip tabStrip;

	public SlidingTabLayout(Context context) {
		this(context, null);
	}

	public SlidingTabLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setHorizontalScrollBarEnabled(false);
		setFillViewport(true);

		titleOffset = (int) (24 * getResources().getDisplayMetrics().density);
		tabStrip = new SlidingTabStrip(context);

		addView(tabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	}

	public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
		pageChangeListener = listener;
	}

	public void setViewPager(ViewPager pager) {
		tabStrip.removeAllViews();

		viewPager = pager;
		if (viewPager != null) {
			viewPager.setOnPageChangeListener(new ViewPagerListener());
			viewPager.getAdapter().registerDataSetObserver(new AdapterObserver());
		}
	}

	protected TextView createTabView(Context context) {
		TextView tabView = new TextView(context, null, R.attr.slidingTabTextViewStyle);

		return tabView;
	}

	private void populateTabStrip() {
		tabStrip.removeAllViews();

		final PagerAdapter adapter = viewPager.getAdapter();
		final View.OnClickListener clickListener = new ClickListener();

		int count = adapter.getCount();

		for (int i = 0; i < count; i++) {
			TextView tabView = createTabView(getContext());

			tabView.setText(adapter.getPageTitle(i));
			tabView.setOnClickListener(clickListener);

			tabStrip.addView(tabView);
		}

		selectTab(viewPager.getCurrentItem());
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		if (viewPager != null) {
			scrollToTab(viewPager.getCurrentItem(), 0);
			selectTab(viewPager.getCurrentItem());
		}
	}

	private void scrollToTab(int tabIndex, int positionOffset) {
		final int tabStripChildCount = tabStrip.getChildCount();

		if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
			return;
		}

		View selectedChild = tabStrip.getChildAt(tabIndex);
		if (selectedChild != null) {
			int targetScrollX = selectedChild.getLeft() + positionOffset;

			if (tabIndex > 0 || positionOffset > 0) {
				targetScrollX -= titleOffset;
			}

			scrollTo(targetScrollX, 0);
		}
	}

	private void selectTab(int tabIndex) {
		for (int i = 0; i < tabStrip.getChildCount(); i++) {
			View childView = tabStrip.getChildAt(i);

			if (TextView.class.isInstance(childView)) {
				TextView tabView = (TextView) childView;
				tabView.setActivated(i == tabIndex);
			}
		}
	}

	private class ViewPagerListener implements ViewPager.OnPageChangeListener {
		private int scrollState;

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			int tabStripChildCount = tabStrip.getChildCount();

			if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
				return;
			}

			tabStrip.onViewPagerPageChanged(position, positionOffset);

			View selectedTitle = tabStrip.getChildAt(position);
			int extraOffset = (selectedTitle != null) ? (int) (positionOffset * selectedTitle.getWidth()) : 0;
			scrollToTab(position, extraOffset);

			if (pageChangeListener != null) {
				pageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}

			if (positionOffset <= 0.01f) {
				selectTab(position);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			scrollState = state;

			if (pageChangeListener != null) {
				pageChangeListener.onPageScrollStateChanged(state);
			}
		}

		@Override
		public void onPageSelected(int position) {
			if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
				tabStrip.onViewPagerPageChanged(position, 0f);
				scrollToTab(position, 0);
			}

			if (pageChangeListener != null) {
				pageChangeListener.onPageSelected(position);
			}
		}

	}

	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			for (int i = 0; i < tabStrip.getChildCount(); i++) {
				if (view == tabStrip.getChildAt(i)) {
					if (TextView.class.isInstance(view)) {
						TextView tabView = (TextView) view;
						tabView.setActivated(true);
					}

					viewPager.setCurrentItem(i);
					return;
				}
			}
		}

	}

	private class AdapterObserver extends DataSetObserver {

		@Override
		public void onChanged() {
			populateTabStrip();
		}

	}

}