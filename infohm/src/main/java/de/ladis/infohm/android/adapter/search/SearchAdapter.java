package de.ladis.infohm.android.adapter.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.ladis.infohm.R;
import de.ladis.infohm.core.domain.Search;

public class SearchAdapter extends BaseAdapter {

	private static class ViewHolder {

		private final TextView titleView;
		private final TextView contentView;

		public ViewHolder(View view) {
			this.titleView = (TextView) view.findViewById(R.id.adapter_search_title);
			this.contentView = (TextView) view.findViewById(R.id.adapter_search_content);
		}

	}

	private final List<Search> items;

	public SearchAdapter() {
		this.items = new ArrayList<Search>();
	}

	public void clearItems() {
		items.clear();
		notifyDataSetChanged();
	}

	public void addItems(Collection<Search> results) {
		for (Search search : results) {
			addItem(search);
		}
	}

	public void addItem(Search search) {
		int index = items.indexOf(search);

		if (index >= 0) {
			items.remove(index);
			items.add(index, search);
		} else {
			items.add(search);
		}

		notifyDataSetChanged();
	}

	@Override
	public Search getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Search search = getItem(position);
		final Context context = parent.getContext();

		ViewHolder holder;

		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_search, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String content = search.getContent();
		String title, body;

		int index = content.indexOf('\n');

		if (index > 0) {
			title = content.substring(0, index);
			body = content.substring(index + 1);
		} else {
			title = body = content;
		}

		holder.titleView.setText(title);
		holder.contentView.setText(body);

		return convertView;
	}

}
