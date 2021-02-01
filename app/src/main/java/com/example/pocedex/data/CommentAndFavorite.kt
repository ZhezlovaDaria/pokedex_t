package com.example.pocedex.data

import android.os.Parcel
import android.os.Parcelable

data class CommentAndFavorite(var name: String? = "", var id: Int = 0, var url: String? = "", var is_favorite: Boolean = false, var comment: String? = "") : Parcelable {


    fun equals(id: Int): Boolean {
        return this.id == id
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommentAndFavorite> {
        override fun createFromParcel(source: Parcel): CommentAndFavorite {
            val name = source.readString()
            val id = source.readInt()
            val url = source.readString()
            val is_fav: Boolean=(source.readInt() == 1)
            val comment = source.readString()
            return CommentAndFavorite(name, id, url, is_fav, comment)
        }

        override fun newArray(size: Int): Array<CommentAndFavorite?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(id)
        parcel.writeString(url)
        if (is_favorite) {
            parcel.writeInt(1)
        } else {
            parcel.writeInt(0)
        }
        parcel.writeString(comment)
    }

}
