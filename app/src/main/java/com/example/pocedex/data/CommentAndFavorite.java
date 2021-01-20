package com.example.pocedex.data;


import android.os.Parcel;
import android.os.Parcelable;

public class CommentAndFavorite implements Parcelable {

    private String name;
    private int id;
    private String url;
    private boolean is_favorite = false;
    private String comment = null;

    public CommentAndFavorite() {
    }

    public CommentAndFavorite(String name, int id, String url, boolean is_favorite, String comment) {
        this.name = name;
        this.id = id;
        this.url = url;
        this.is_favorite = is_favorite;
        this.comment = comment;
    }


    public boolean equals(int id) {
        if (this.id == id) {
            return true;
        }
        return false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUrl(String link) {
        this.url = link;
    }

    public String getUrl() {
        return url;
    }

    public void setIsFav(boolean isfavorite) {
        is_favorite = isfavorite;
    }

    public boolean getIsFav() {
        return is_favorite;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<CommentAndFavorite> CREATOR = new Parcelable.Creator<CommentAndFavorite>() {

        @Override
        public CommentAndFavorite createFromParcel(Parcel source) {
            String name = source.readString();
            int id = source.readInt();
            String url = source.readString();
            boolean is_fav;
            if (source.readInt() == 1)
                is_fav = true;
            else is_fav = false;
            String comment = source.readString();
            return new CommentAndFavorite(name, id, url, is_fav, comment);
        }

        @Override
        public CommentAndFavorite[] newArray(int size) {
            return new CommentAndFavorite[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeString(url);
        if (is_favorite) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(comment);
    }

}
