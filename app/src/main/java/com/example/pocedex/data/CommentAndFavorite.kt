package com.example.pocedex.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.android.parcel.TypeParceler

data class CommentAndFavorite(var pokemon: Pokemon=Pokemon(), var is_favorite: Boolean = false, var comment: String? = "") : Parcelable {


    fun alreadyExists(id: Int): Boolean {
        return this.pokemon.id == id
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommentAndFavorite> {
        override fun createFromParcel(source: Parcel): CommentAndFavorite {
            val gson = Gson()
            val pokemon: Pokemon = gson.fromJson(source.readString(), Pokemon::class.java)
            val isFav: Boolean=(source.readInt() == 1)
            val comment = source.readString()
            return CommentAndFavorite(pokemon, isFav, comment)
        }

        override fun newArray(size: Int): Array<CommentAndFavorite?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        val gson = Gson()
        val jsonString = gson.toJson(pokemon)
        parcel.writeString(jsonString)
        if (is_favorite) {
            parcel.writeInt(1)
        } else {
            parcel.writeInt(0)
        }
        parcel.writeString(comment)
    }

}
