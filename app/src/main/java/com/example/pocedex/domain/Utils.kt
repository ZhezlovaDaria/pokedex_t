package com.example.pocedex.domain

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.pocedex.data.CommentAndFavorite
import com.example.pocedex.data.Pokemon
import java.util.ArrayList

internal open class Utils {

    companion object {
        val preferenses = "commandfav"

        var localSave: LocalSave? = null

        private var notRandom: MutableList<Int> =ArrayList()

        val notRandomNumbers: MutableList<Int>
            get() {
                val commentAndFavorite = localSave!!.getCommentAndFavorites()
                notRandom = ArrayList()
                for (i in commentAndFavorite.indices) {
                    if (commentAndFavorite[i].getIsFav())
                        notRandom!!.add(commentAndFavorite[i].getId())
                }
                return notRandom
            }

        fun isOnline(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo

            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

        fun findOnId(
                id: Int, caf: ArrayList<CommentAndFavorite>): CommentAndFavorite? {
            try {
                for (c in caf) {
                    if (c.equals(id)) {
                        return c
                    }
                }
            } catch (e: Exception) {
                Log.d("", e.message)
            }

            return null
        }

        fun save(pokemon: Pokemon, commentAndFavorite: CommentAndFavorite) {
            val commentAndFavorite1Check = findOnId(pokemon.getId(), localSave!!.getCommentAndFavorites())
            if (commentAndFavorite1Check == null) {
                localSave!!.addToCommentAndFavorites(commentAndFavorite)
            }
            try {
                localSave!!.save()
            } catch (e: Exception) {
                Log.d("comfavsave", e.message)
            }

        }

        fun saveShowDialog(show: Boolean) {
            localSave!!.saveMenu(show)
        }

        fun openShowDialog(): Boolean {
            localSave!!.openMenu()
            return localSave!!.getShowDialod()
        }
    }
}
