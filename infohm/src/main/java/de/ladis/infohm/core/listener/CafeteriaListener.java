package de.ladis.infohm.core.listener;

import java.util.List;

import de.ladis.infohm.core.domain.Cafeteria;

public interface CafeteriaListener {

	public void onUpdated(List<Cafeteria> cafeterias);

	public void onGathered(List<Cafeteria> cafeterias);

}
