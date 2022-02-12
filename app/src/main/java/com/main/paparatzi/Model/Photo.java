package com.main.paparatzi.Model;
import android.os.Parcel;
import android.os.Parcelable;


public class Photo implements Parcelable {
    private String date;
    private String title;
    private String body;
    private String imageId;
    private String photoNumber;
    private String userId;
    private double lat,lon;
    public Photo() {

    }



    public Photo(String title, String body, String imageId, String userId) {
        super();
        this.title = title;
        this.body = body;
        this.imageId = imageId;
        this.userId = userId;
    }
    public static final Parcelable.Creator<Photo> CREATOR
            = new Parcelable.Creator<Photo>() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    protected Photo(Parcel in) {
        title = in.readString();
        body = in.readString();
        imageId = in.readString();
        date=in.readString();
        photoNumber =in.readString();
        userId =in.readString();
        lat=in.readDouble();
        lon=in.readDouble();
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeString(imageId);
        parcel.writeString(date);
        parcel.writeString(photoNumber);
        parcel.writeString(userId);
        parcel.writeDouble(lat);
        parcel.writeDouble(lon);
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getPhotoNumber() {
        return photoNumber;
    }

    public void setPhotoNumber(String photoNumber) {
        this.photoNumber = photoNumber;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
