package de.ladis.infohm.android.adapter.multichoice;

public interface MultichoiceItemBinder<T> {

	public String getTitle(T item);

	public String getCaption(T item);

}