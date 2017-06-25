package de.ladis.infohm.android.adapter.publisher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import de.ladis.infohm.R;
import de.ladis.infohm.core.domain.Publisher;

public class StarPublisherAdapter extends RecyclerView.Adapter<StarPublisherAdapter.ViewHolder> {

	protected static class ViewHolder extends RecyclerView.ViewHolder {

		private final View parentView;
		private final CheckBox checkView;
		private final TextView titleView;
		private final TextView captionView;

		public ViewHolder(View view) {
			super(view);

			this.parentView = view;
			this.checkView = (CheckBox) view.findViewById(R.id.adapter_star_publisher_checkbox);
			this.titleView = (TextView) view.findViewById(R.id.adapter_star_publisher_name);
			this.captionView = (TextView) view.findViewById(R.id.adapter_star_publisher_description);
		}

	}

	private final List<Publisher> items;
	private final Set<Integer> selection;

	public StarPublisherAdapter() {
		this.items = new ArrayList<Publisher>();
		this.selection = new HashSet<Integer>();
	}

	public boolean containsItem(Publisher publisher) {
		return items.indexOf(publisher) >= 0;
	}

	public void addItems(Collection<Publisher> publishers) {
		for (Publisher publisher : publishers) {
			addItem(publisher);
		}
	}

	public void addItem(Publisher publisher) {
		int index = items.indexOf(publisher);

		if (index >= 0) {
			items.remove(index);
			items.add(index, publisher);

			notifyItemChanged(index);
		} else {
			index = items.size();

			items.add(publisher);

			notifyItemInserted(index);
		}
	}

	public void selectItem(Publisher item) {
		int position = items.indexOf(item);

		if (position >= 0 && selection.add(position)) {
			notifyDataSetChanged();
		}
	}

	public void deselectItem(Publisher item) {
		int position = items.indexOf(item);

		if (position >= 0 && selection.remove(position)) {
			notifyDataSetChanged();
		}
	}

	private void toggleSelect(int position) {
		if (selection.contains(position)) {
			selection.remove(position);
		} else {
			selection.add(position);
		}

		notifyDataSetChanged();
	}

	public List<Publisher> getSelection() {
		List<Publisher> items = new ArrayList<Publisher>(selection.size());

		for (Integer position : selection) {
			items.add(getItem(position));
		}

		return items;
	}

	public Publisher getItem(int position) {
		return items.get(position);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();

		View view = LayoutInflater.from(context).inflate(R.layout.adapter_star_publisher, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		Publisher item = getItem(position);

		holder.checkView.setChecked(selection.contains(position));
		holder.titleView.setText(item.getName());
		holder.captionView.setText(item.getDescription());

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				toggleSelect(position);
			}

		};

		holder.parentView.setOnClickListener(listener);
		holder.checkView.setOnClickListener(listener);
	}

}
