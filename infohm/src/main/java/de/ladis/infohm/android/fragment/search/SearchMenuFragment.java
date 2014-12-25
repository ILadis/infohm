package de.ladis.infohm.android.fragment.search;

import javax.inject.Inject;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.search.SearchActivity;
import de.ladis.infohm.android.fragment.BaseFragment;

public class SearchMenuFragment extends BaseFragment {

	@Inject
	protected SearchManager manager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return new View(inflater.getContext());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_search, menu);

		MenuItem item = menu.findItem(R.id.fragment_search_menu);

		SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
		searchView.setSearchableInfo(manager.getSearchableInfo(new ComponentName(getActivity(), SearchActivity.class)));

		String query = getQuery();

		if (query != null) {
			searchView.setIconified(false);
			searchView.clearFocus();
			searchView.setQuery(query, false);
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

}
