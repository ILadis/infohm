package de.ladis.infohm.android.fragment.publisher;

import java.util.Collection;

import de.ladis.infohm.android.adapter.multichoice.MultichoiceItemBinder;
import de.ladis.infohm.android.adapter.multichoice.MultichoiceAdapter;
import de.ladis.infohm.core.domain.Publisher;

public class StarPublisherAdapter extends MultichoiceAdapter<Publisher> {

	private final static MultichoiceItemBinder<Publisher> BINDER = new MultichoiceItemBinder<Publisher>() {

		@Override
		public String getTitle(Publisher item) {
			return item.getName();
		}

		@Override
		public String getCaption(Publisher item) {
			return item.getDescription();
		}

	};

	public StarPublisherAdapter(Collection<Publisher> items) {
		super(items, BINDER);
	}

}
