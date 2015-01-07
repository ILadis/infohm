package de.ladis.infohm.android.adapter.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.widget.TimestampView;
import de.ladis.infohm.core.domain.Event;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

	protected static class ViewHolder extends RecyclerView.ViewHolder {

		private final TextView headlineView;
		private final TimestampView timestampView;
		private final TextView contentView;

		public ViewHolder(View view) {
			super(view);

			this.headlineView = (TextView) view.findViewById(R.id.adapter_events_headline);
			this.timestampView = (TimestampView) view.findViewById(R.id.adapter_events_timestamp);
			this.contentView = (TextView) view.findViewById(R.id.adapter_events_content);
		}

	}

	private static class EventComparator implements Comparator<Event> {

		@Override
		public int compare(Event lhs, Event rhs) {
			return rhs.getCreatedAt().compareTo(lhs.getCreatedAt());
		}

	}

	private final List<Event> items;
	private final EventComparator comparator;

	public EventsAdapter() {
		this.items = new ArrayList<Event>();
		this.comparator = new EventComparator();
	}

	public void addItems(Collection<Event> publishers) {
		for (Event publisher : publishers) {
			addItem(publisher);
		}
	}

	public void addItem(Event event) {
		int index = items.indexOf(event);

		if (index >= 0) {
			items.remove(index);
			items.add(index, event);

			notifyItemChanged(index);

			Collections.sort(items, comparator);
			int newIndex = items.indexOf(event);

			if (index != newIndex) {
				notifyItemMoved(index, newIndex);
			}
		} else {
			items.add(event);
			Collections.sort(items, comparator);

			index = items.indexOf(event);

			notifyItemInserted(index);
		}
	}

	public void clearItems() {
		items.clear();
		notifyDataSetChanged();
	}

	public Event getItem(int position) {
		return items.get(position);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();

		View view = LayoutInflater.from(context).inflate(R.layout.adapter_events, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Event event = getItem(position);

		holder.headlineView.setText(event.getHeadline());
		holder.timestampView.setTimestamp(event.getCreatedAt());
		holder.timestampView.setText("%T");
		holder.contentView.setText(event.getContent());
	}

}
