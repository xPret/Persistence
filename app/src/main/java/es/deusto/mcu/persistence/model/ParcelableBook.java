package es.deusto.mcu.persistence.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableBook extends Book implements Parcelable {

    public ParcelableBook(Book book) {
        this.setTitle(book.getTitle());
        this.setAuthor(book.getAuthor());
        this.setPublisher(book.getPublisher());
        this.setPublishedDate(book.getPublishedDate());
        this.setAverageRating(book.getAverageRating());
        this.setThumbnail(book.getThumbnail());
    }

    public static final Creator<ParcelableBook> CREATOR = new Creator<ParcelableBook>() {
        @Override
        public ParcelableBook createFromParcel(Parcel in) {
            return new ParcelableBook(in);
        }

        @Override
        public ParcelableBook[] newArray(int size) {
            return new ParcelableBook[size];
        }
    };

    protected ParcelableBook(Parcel parcel) {
        this.setTitle(parcel.readString());
        this.setAuthor(parcel.readString());
        this.setPublisher(parcel.readString());
        this.setPublishedDate(parcel.readString());
        this.setAverageRating(parcel.readInt());
        this.setThumbnail(parcel.readString());
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.getTitle());
        parcel.writeString(this.getAuthor());
        parcel.writeString(this.getPublisher());
        parcel.writeString(this.getPublishedDate());
        parcel.writeInt(this.getAverageRating());
        parcel.writeString(this.getThumbnail());
    }

    @Override
    public int describeContents() {
        return 0;
    }

}