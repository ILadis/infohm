package de.ladis.infohm.android.adapter.bookmarks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.ladis.infohm.R;
import de.ladis.infohm.core.domain.Bookmark;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BookmarksAdapter extends BaseAdapter {

	private static class ViewHolder {

		private final View parentView;
		private final TextView titleView;
		private final TextView descriptionView;

		public ViewHolder(View view) {
			this.parentView = view;
			this.titleView = (TextView) view.findViewById(R.id.adapter_bookmarks_title);
			this.descriptionView = (TextView) view.findViewById(R.id.adapter_bookmarks_description);
		}

	}

	private final List<Bookmark> items;

	public BookmarksAdapter() {
		this.items = new ArrayList<Bookmark>();
	}

	public void addItems(Collection<Bookmark> bookmarks) {
		for (Bookmark bookmark : bookmarks) {
			addItem(bookmark);
		}
	}

	public void addItem(Bookmark bookmark) {
		int index = items.indexOf(bookmark);

		if (index >= 0) {
			items.remove(index);
			items.add(index, bookmark);
		} else {
			items.add(bookmark);
		}

		notifyDataSetChanged();
	}

	@Override
	public Bookmark getItem(int position) {
		return items.get(position);
	}

	public void clearItems() {
		items.clear();
		notifyDataSetChanged();
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
		final Bookmark bookmark = getItem(position);
		final Context context = parent.getContext();

		ViewHolder holder;

		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_bookmarks, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				launchBrowser(bookmark, context);
			}

		};

		holder.parentView.setOnClickListener(listener);
		holder.titleView.setText(bookmark.getTitle());
		holder.descriptionView.setText(bookmark.getDescription());

		return convertView;
	}

	private void launchBrowser(Bookmark bookmark, Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(bookmark.getUrl()));

		context.startActivity(intent);
	}

}
