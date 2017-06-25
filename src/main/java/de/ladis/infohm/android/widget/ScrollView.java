package de.ladis.infohm.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ScrollView extends android.widget.ScrollView {

	private OnScrollableListener listener;

	public ScrollView(Context context) {
		this(context, null);
	}

	public ScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setOnScrollableListener(OnScrollableListener listener) {
		this.listener = listener;

		requestLayout();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		if (listener != null) {
			listener.onCanScroll(this, canScroll());
		}
	}

	private boolean canScroll() {
		View child = getChildAt(0);

		if (child != null) {
			int childHeight = child.getHeight();
			return getHeight() < childHeight + getPaddingTop() + getPaddingBottom();
		}

		return false;
	}

	public static interface OnScrollableListener {

		public void onCanScroll(ScrollView view, boolean scrollable);

	}

}
