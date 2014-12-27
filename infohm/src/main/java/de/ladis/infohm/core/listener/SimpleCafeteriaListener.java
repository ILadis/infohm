package de.ladis.infohm.core.listener;

import java.util.List;

import de.ladis.infohm.core.domain.Cafeteria;

public abstract class SimpleCafeteriaListener implements CafeteriaListener {

	@Override
	public void onUpdated(List<Cafeteria> cafeterias) {
	}

	@Override
	public void onGathered(List<Cafeteria> cafeterias) {
	}

}
