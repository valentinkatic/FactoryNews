package com.katic.factorynews;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable{

    private String title;
    private String description;
    private String urlToImage;

    public Article(String title, String description, String urlToImage) {
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
    }

    public Article(Parcel in){
        super();
        readFromParcel(in);
    }

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void readFromParcel(Parcel in){
        this.title = in.readString();
        this.description = in.readString();
        this.urlToImage = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(urlToImage);
    }

    public int describeContents(){
        return 0;
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {

        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

}
