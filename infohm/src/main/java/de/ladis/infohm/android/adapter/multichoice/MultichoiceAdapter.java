package de.ladis.infohm.android.adapter.multichoice;

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
import de.ladis.infohm.android.adapter.multichoice.MultichoiceAdapter.ViewHolder;

public class MultichoiceAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

	protected static class ViewHolder extends RecyclerView.ViewHolder {

		private final View parentView;
		private final CheckBox checkView;
		private final TextView titleView;
		private final TextView captionView;

		public ViewHolder(View view) {
			super(view);

			this.parentView = view;
			this.checkView = (CheckBox) view.findViewById(R.id.adapter_multichoice_checkbox);
			this.titleView = (TextView) view.findViewById(R.id.adapter_multichoice_title);
			this.captionView = (TextView) view.findViewById(R.id.adapter_multichoice_caption);
		}

	}

	private final List<T> items;
	private final MultichoiceItemBinder<T> binder;
	private final Set<Integer> selection;

	public MultichoiceAdapter(Collection<T> items, MultichoiceItemBinder<T> binder) {
		this.items = new ArrayList<T>(items);
		this.binder = binder;
		this.selection = new HashSet<Integer>();
	}

	public void selectItem(T item) {
		int position = items.indexOf(item);

		if (position >= 0) {
			select(position);
		}
	}

	private void select(int position) {
		if (selection.contains(position)) {
			selection.remove(position);
		} else {
			selection.add(position);
		}

		notifyDataSetChanged();
	}

	public Collection<T> getSelection() {
		List<T> items = new ArrayList<T>(selection.size());

		for (Integer position : selection) {
			items.add(getItem(position));
		}

		return items;
	}

	public T getItem(int position) {
		return items.get(position);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();

		View view = LayoutInflater.from(context).inflate(R.layout.adapter_multichoice, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		T item = getItem(position);

		holder.checkView.setChecked(selection.contains(position));
		holder.titleView.setText(binder.getTitle(item));
		holder.captionView.setText(binder.getCaption(item));

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				select(position);
			}

		};

		holder.parentView.setOnClickListener(listener);
		holder.checkView.setOnClickListener(listener);
	}

}
