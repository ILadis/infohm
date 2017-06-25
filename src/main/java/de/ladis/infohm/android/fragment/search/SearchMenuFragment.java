package de.ladis.infohm.android.fragment.search;

import static android.support.v4.view.MenuItemCompat.*;

import javax.inject.Inject;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView.OnSuggestionListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import de.ladis.infohm.R;
import de.ladis.infohm.android.activity.search.SearchActivity;
import de.ladis.infohm.android.controller.SearchController;
import de.ladis.infohm.android.fragment.BaseFragment;

public class SearchMenuFragment extends BaseFragment {

	private SearchController controller;

	@Inject
	protected SearchManager manager;

	@Inject
	protected SearchRecentSuggestions suggestions;

	private MenuItem menuItem;

	private SearchView searchView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			controller = (SearchController) activity;
		} catch (ClassCastException e) { }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return new View(inflater.getContext());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_search, menu);

		menuItem = menu.findItem(R.id.fragment_search_menu);

		searchView = (SearchView) getActionView(menuItem);
		searchView.setSearchableInfo(manager.getSearchableInfo(new ComponentName(getActivity(), SearchActivity.class)));
		searchView.setOnQueryTextListener(queryListener);
		searchView.setOnSuggestionListener(suggestionListener);
		searchView.setOnCloseListener(closeListener);

		String query = obtainQuery();

		if (query != null) {
			expandSearch(query);
		}
	}

	private String obtainQuery() {
		Intent intent = getActivity().getIntent();

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);

			return query;
		}

		return null;
	}

	private String obtainSuggestion(int position) {
		Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
		String suggestion = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));

		return suggestion;
	}

	private void expandSearch(String query) {
		expandActionView(menuItem);

		searchView.setIconified(false);
		searchView.setQuery(query, false);
		searchView.clearFocus();
	}

	private void collapseSearch() {
		searchView.setQuery("", false);
		searchView.clearFocus();
		searchView.setIconified(true);

		collapseActionView(menuItem);
	}

	private void saveSuggestion(String query) {
		suggestions.saveRecentQuery(query, null);
	}

	private OnQueryTextListener queryListener = new OnQueryTextListener() {

		@Override
		public boolean onQueryTextChange(String query) {
			if (controller != null) {
				controller.onQueryChanged(query);
				return true;
			}

			return false;
		}

		@Override
		public boolean onQueryTextSubmit(String query) {
			if (controller != null) {
				controller.onQuerySubmitted(query);
				return true;
			}

			collapseSearch();
			saveSuggestion(query);

			return false;
		}

	};

	private OnSuggestionListener suggestionListener = new OnSuggestionListener() {

		@Override
		public boolean onSuggestionClick(int position) {
			if (controller != null) {
				String query = obtainSuggestion(position);

				searchView.setQuery(query, false);
				controller.onQuerySubmitted(query);

				return true;
			}

			collapseSearch();

			return false;
		}

		@Override
		public boolean onSuggestionSelect(int position) {
			return false;
		}
	};

	private OnCloseListener closeListener = new OnCloseListener() {

		@Override
		public boolean onClose() {
			if (controller != null) {
				controller.onQueryClosed();
			}

			return false;
		}

	};

}
