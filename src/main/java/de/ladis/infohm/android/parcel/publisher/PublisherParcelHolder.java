package de.ladis.infohm.android.parcel.publisher;

import org.joda.time.DateTime;

import android.os.Parcel;
import android.os.Parcelable;
import de.ladis.infohm.android.parcel.ParcelHolder;
import de.ladis.infohm.core.domain.Publisher;

public class PublisherParcelHolder extends ParcelHolder<Publisher> {

	public static final Parcelable.Creator<PublisherParcelHolder> CREATOR = new Parcelable.Creator<PublisherParcelHolder>() {

		@Override
		public PublisherParcelHolder createFromParcel(Parcel parcel) {
			return new PublisherParcelHolder(parcel);
		}

		@Override
		public PublisherParcelHolder[] newArray(int size) {
			return new PublisherParcelHolder[size];
		}

	};

	public PublisherParcelHolder(Publisher entity) {
		super(entity);
	}

	public PublisherParcelHolder(Parcel parcel) {
		super(parcel);
	}

	@Override
	protected void writeToParcel(Publisher entity, Parcel parcel) {
		parcel.writeLong(entity.getId());
		parcel.writeString(entity.getName());
		parcel.writeString(entity.getDescription());
		parcel.writeString(entity.getCreatedAt().toString());
		parcel.writeString(entity.getUpdatedAt().toString());
	}

	@Override
	protected Publisher readFromParcel(Parcel parcel) {
		Publisher publisher = new Publisher();

		publisher.setId(parcel.readLong());
		publisher.setName(parcel.readString());
		publisher.setDescription(parcel.readString());
		publisher.setCreatedAt(DateTime.parse(parcel.readString()));
		publisher.setUpdatedAt(DateTime.parse(parcel.readString()));

		return publisher;
	}

}
