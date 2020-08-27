package com.example.pocedex.data;


import android.os.Parcel;
import android.os.Parcelable;

public class CommAndFav implements Parcelable {

    private String name;
    private int id;
    private String url;
    private boolean is_fav = false;
    private String comment = null;

    public CommAndFav() {
    }

    public CommAndFav(String name, int id, String url, boolean is_fav, String comment) {
        this.name = name;
        this.id = id;
        this.url = url;
        this.is_fav = is_fav;
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

    public void setIsFav(boolean iffav) {
        is_fav = iffav;
    }

    public boolean getIsFav() {
        return is_fav;
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

    public static final Parcelable.Creator<CommAndFav> CREATOR = new Parcelable.Creator<CommAndFav>() {

        @Override
        public CommAndFav createFromParcel(Parcel source) {
            String name = source.readString();
            int id = source.readInt();
            String url = source.readString();
            boolean is_fav;
            if (source.readInt() == 1)
                is_fav = true;
            else is_fav = false;
            String comment = source.readString();
            return new CommAndFav(name, id, url, is_fav, comment);
        }

        @Override
        public CommAndFav[] newArray(int size) {
            return new CommAndFav[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeString(url);
        if (is_fav) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(comment);
    }

}
