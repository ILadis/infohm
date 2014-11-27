package de.ladis.infohm.android.widget;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;

public class TextView extends android.widget.TextView {

	public TextView(Context context) {
		super(context);
	}

	public TextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(Html.fromHtml(text.toString()), type);
	}

}