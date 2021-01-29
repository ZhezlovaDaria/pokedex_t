package com.example.pocedex.data

import android.os.Parcel
import android.os.Parcelable

class CommentAndFavorite(_name: String?="", _id: Int=0, _url: String?="", _is_favorite: Boolean=false, _comment: String?="") : Parcelable
{

    private var name: String? = ""
    private var id: Int = 0
    private var url: String? = ""
    private var is_favorite = false
    private var comment: String? = ""

    init
    {
        name = _name;
        id = _id;
        url = _url;
        is_favorite = _is_favorite;
        comment = _comment;
    }

    fun equals(id: Int): Boolean {
        return if (this.id == id) {
            true
        } else false
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String? {
        return name
    }

    fun setUrl(link: String) {
        this.url = link
    }

    fun getUrl(): String? {
        return url
    }

    fun setIsFav(isfavorite: Boolean) {
        is_favorite = isfavorite
    }

    fun getIsFav(): Boolean {
        return is_favorite
    }

    fun setComment(comment: String) {
        this.comment = comment
    }

    fun getComment(): String? {
        return comment
    }

    override fun describeContents(): Int {
        return 0
    }
    @JvmField
    val CREATOR: Parcelable.Creator<CommentAndFavorite> = object : Parcelable.Creator<CommentAndFavorite> {

        override fun createFromParcel(source: Parcel): CommentAndFavorite {
            val name = source.readString()
            val id = source.readInt()
            val url = source.readString()
            val is_fav: Boolean
            if (source.readInt() == 1)
                is_fav = true
            else
                is_fav = false
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
