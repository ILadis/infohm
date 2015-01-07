package de.ladis.infohm.android.widget;

import org.joda.time.DateTime;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import de.ladis.infohm.util.TimestampFormatter;

public class TimestampView extends TextView {

	private TimestampFormatter formatter;
	private DateTime timestamp;

	public TimestampView(Context context) {
		super(context);
	}

	public TimestampView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TimestampView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TimestampFormatter getFormatter() {
		if (formatter == null) {
			formatter = TimestampFormatter.from(getContext());
		}

		return formatter;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		String string = text.toString();

		if (timestamp != null) {
			string = getFormatter().format(string, timestamp);
		}

		super.setText(string, type);
	}

}
