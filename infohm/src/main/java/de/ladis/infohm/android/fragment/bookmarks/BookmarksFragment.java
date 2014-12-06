package de.ladis.infohm.android.fragment.bookmarks;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.adapter.bookmarks.BookmarksAdapter;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.core.domain.Bookmark;
import de.ladis.infohm.core.listener.SimpleBookmarkListener;
import de.ladis.infohm.core.service.BookmarkService;

public class BookmarksFragment extends BaseFragment {

	@Inject
	protected BookmarkService service;

	@InjectView(R.id.fragment_bookmarks_list)
	protected ListView listView;

	private BookmarksAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new BookmarksAdapter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bookmarks, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		listView.setAdapter(adapter);
	}

	@Override
	public void onResume() {
		super.onResume();

		service.registerListener(listener);
		service.getAll().doAsync();
	}

	private final SimpleBookmarkListener listener = new SimpleBookmarkListener() {

		@Override
		public void onGathered(List<Bookmark> bookmarks) {
			if (bookmarks.size() <= 0) {
				service.updateAll().doAsync();
			} else {
				adapter.addItems(bookmarks);
			}
		}

		@Override
		public void onUpdated(List<Bookmark> bookmarks) {
			if (bookmarks != null) {
				adapter.addItems(bookmarks);
			}
		}

	};

	@Override
	public void onPause() {
		super.onPause();

		service.unregisterListener(listener);
	}

}
