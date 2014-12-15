package de.ladis.infohm.android.widget;

import org.joda.time.DateTime;
import org.joda.time.Period;

import de.ladis.infohm.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.TextView;

public class TimestampView extends TextView {

	public TimestampView(Context context) {
		super(context);
	}

	public TimestampView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TimestampView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setText(DateTime timestamp) {
		DateTime now = DateTime.now();
		Period period = new Period(timestamp, now);

		Resources resources = getResources();
		String text;

		if (period.getWeeks() > 0 || period.getMonths() > 0 || period.getYears() > 0) {
			text = resources.getString(R.string.view_timestamp_at,
					String.format("%04d", timestamp.getYear()),
					String.format("%02d", timestamp.getMonthOfYear()),
					String.format("%02d", timestamp.getDayOfMonth()),
					String.format("%02d", timestamp.getHourOfDay()),
					String.format("%02d", timestamp.getMinuteOfHour()));
		} else if (period.getDays() > 0) {
			text = resources.getQuantityString(R.plurals.view_timestamp_days_ago, period.getDays(), period.getDays());
		} else if (period.getHours() > 0) {
			text = resources.getQuantityString(R.plurals.view_timestamp_hours_ago, period.getHours(), period.getHours());
		} else if (period.getMinutes() > 0) {
			text = resources.getQuantityString(R.plurals.view_timestamp_minutes_ago, period.getMinutes(), period.getMinutes());
		} else if (period.getSeconds() >= 15) {
			text = resources.getQuantityString(R.plurals.view_timestamp_seconds_ago, period.getSeconds(), period.getSeconds());
		} else {
			text = resources.getString(R.string.view_timestamp_just_now);
		}

		setText(text);
	}

}
