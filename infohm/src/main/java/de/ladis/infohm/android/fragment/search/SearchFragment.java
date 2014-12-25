package de.ladis.infohm.android.fragment.search;

import java.util.List;

import javax.inject.Inject;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.InjectView;
import de.ladis.infohm.R;
import de.ladis.infohm.android.adapter.search.SearchAdapter;
import de.ladis.infohm.android.fragment.BaseFragment;
import de.ladis.infohm.core.domain.Search;
import de.ladis.infohm.core.listener.SimpleSearchListener;
import de.ladis.infohm.core.service.SearchService;

public class SearchFragment extends BaseFragment {

	@Inject
	protected SearchService service;

	@InjectView(R.id.fragment_search_list)
	protected ListView listView;

	private SearchAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new SearchAdapter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_search, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		listView.setAdapter(adapter);
	}

	@Override
	public void onResume() {
		super.onResume();

		String query = getQuery();

		service.registerListener(listener);
		if (query != null) {
			service.searchFor(query).doAsync();
		}
	}

	private String getQuery() {
		Intent intent = getActivity().getIntent();

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);

			return query;
		}

		return null;
	}

	private SimpleSearchListener listener = new SimpleSearchListener() {

		@Override
		public void onSearchCompleted(List<Search> results) {
			adapter.addItems(results);
		}

	};

	@Override
	public void onPause() {
		super.onPause();

		service.unregisterListener(listener);
	}

}
