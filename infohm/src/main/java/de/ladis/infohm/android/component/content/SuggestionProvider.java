package de.ladis.infohm.android.component.content;

import android.content.SearchRecentSuggestionsProvider;

public class SuggestionProvider extends SearchRecentSuggestionsProvider {

	public SuggestionProvider() {
		setupSuggestions("de.ladis.infohm.provider.SuggestionProvider", DATABASE_MODE_QUERIES);
	}

}
