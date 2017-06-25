package de.ladis.infohm.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import de.ladis.infohm.R;

class SlidingTabStrip extends LinearLayout {

	private final int indicatorThickness;
	private final Paint indicatorPaint;

	private int selectedPosition;
	private float selectionOffset;

	public SlidingTabStrip(Context context) {
		this(context, null);
	}

	public SlidingTabStrip(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);

		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(R.attr.slidingTabIndicatorColor, typedValue, true);

		final int indicatorColor = typedValue.data;

		context.getTheme().resolveAttribute(R.attr.slidingTabIndicatorHeight, typedValue, true);
		TypedArray typedArray = context.obtainStyledAttributes(typedValue.resourceId, new int[] { R.attr.slidingTabIndicatorHeight });

		final int indicatorHeight = typedArray.getDimensionPixelSize(0, -1);

		indicatorThickness = (indicatorHeight);

		indicatorPaint = new Paint();
		indicatorPaint.setColor(indicatorColor);
	}

	public void onViewPagerPageChanged(int position, float positionOffset) {
		selectedPosition = position;
		selectionOffset = positionOffset;

		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		final int height = getHeight();
		final int childCount = getChildCount();

		if (childCount > 0) {
			View selectedTitle = getChildAt(selectedPosition);
			int left = selectedTitle.getLeft();
			int right = selectedTitle.getRight();

			if (selectionOffset > 0f && selectedPosition < (getChildCount() - 1)) {
				View nextTitle = getChildAt(selectedPosition + 1);

				left = (int) (selectionOffset * nextTitle.getLeft() + (1.0f - selectionOffset) * left);
				right = (int) (selectionOffset * nextTitle.getRight() + (1.0f - selectionOffset) * right);
			}

			canvas.drawRect(left, height - indicatorThickness, right, height, indicatorPaint);
		}
	}

}