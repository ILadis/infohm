package de.ladis.infohm.android.parcel;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class ParcelHolder<T> implements Parcelable {

	private final T entity;

	public ParcelHolder(T entity) {
		this.entity = entity;
	}

	public ParcelHolder(Parcel parcel) {
		this.entity = readFromParcel(parcel);
	}

	public T get() {
		return entity;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public final void writeToParcel(Parcel parcel, int flags) {
		writeToParcel(entity, parcel);
	}

	protected abstract void writeToParcel(T entity, Parcel parcel);

	protected abstract T readFromParcel(Parcel parcel);

}
