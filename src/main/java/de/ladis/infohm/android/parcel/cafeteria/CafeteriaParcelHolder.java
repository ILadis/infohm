package de.ladis.infohm.android.parcel.cafeteria;

import org.joda.time.DateTime;

import android.os.Parcel;
import android.os.Parcelable;
import de.ladis.infohm.android.parcel.ParcelHolder;
import de.ladis.infohm.core.domain.Cafeteria;

public class CafeteriaParcelHolder extends ParcelHolder<Cafeteria> {

	public static final Parcelable.Creator<CafeteriaParcelHolder> CREATOR = new Parcelable.Creator<CafeteriaParcelHolder>() {

		@Override
		public CafeteriaParcelHolder createFromParcel(Parcel parcel) {
			return new CafeteriaParcelHolder(parcel);
		}

		@Override
		public CafeteriaParcelHolder[] newArray(int size) {
			return new CafeteriaParcelHolder[size];
		}

	};

	public CafeteriaParcelHolder(Cafeteria entity) {
		super(entity);
	}

	public CafeteriaParcelHolder(Parcel parcel) {
		super(parcel);
	}

	@Override
	protected void writeToParcel(Cafeteria entity, Parcel parcel) {
		parcel.writeLong(entity.getId());
		parcel.writeString(entity.getName());
		parcel.writeDouble(entity.getLongitude());
		parcel.writeDouble(entity.getLatitude());
		parcel.writeString(entity.getCreatedAt().toString());
		parcel.writeString(entity.getUpdatedAt().toString());
	}

	@Override
	protected Cafeteria readFromParcel(Parcel parcel) {
		Cafeteria cafeteria = new Cafeteria();

		cafeteria.setId(parcel.readLong());
		cafeteria.setName(parcel.readString());
		cafeteria.setLongitude(parcel.readDouble());
		cafeteria.setLatitude(parcel.readDouble());
		cafeteria.setCreatedAt(DateTime.parse(parcel.readString()));
		cafeteria.setUpdatedAt(DateTime.parse(parcel.readString()));

		return cafeteria;
	}

}
