package de.ladis.infohm.android.controller;

public interface SearchController {

	public void onQueryChanged(String query);

	public void onQuerySubmitted(String query);

	public void onQueryClosed();

}
