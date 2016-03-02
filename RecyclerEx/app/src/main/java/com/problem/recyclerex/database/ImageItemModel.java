package com.problem.recyclerex.database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by avin on 03/03/16.
 */
public class ImageItemModel implements Parcelable{
    private String mId, mUrl, mTitle;

    public ImageItemModel() {
    }

    public ImageItemModel(String mId, String mUrl, String mTitle) {
        this.mId = mId;
        this.mUrl = mUrl;
        this.mTitle = mTitle;
    }

    protected ImageItemModel(Parcel in) {
        mId = in.readString();
        mUrl = in.readString();
        mTitle = in.readString();
    }

    public static final Creator<ImageItemModel> CREATOR = new Creator<ImageItemModel>() {
        @Override
        public ImageItemModel createFromParcel(Parcel in) {
            return new ImageItemModel(in);
        }

        @Override
        public ImageItemModel[] newArray(int size) {
            return new ImageItemModel[size];
        }
    };

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mUrl);
        dest.writeString(mTitle);
    }
}
