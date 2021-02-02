package com.example.pocedex.domain

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.pocedex.data.CommentAndFavorite
import com.example.pocedex.data.Pokemon
import java.util.*

internal open class Utils {

    companion object {
        const val preferenses = "commandfav"
        var localSave: LocalSave? = null
        private var randomNumbers: ArrayList<Int> = ArrayList()

        fun randomNumbers(count: Int): Int {
            val random = Random()
            if (randomNumbers.isEmpty()) {
                val commentAndFavorite = localSave!!.getCommentAndFavorites()
                if (commentAndFavorite.size == count)
                    return -1
                val notRandom: ArrayList<Int> = ArrayList()
                for (i in commentAndFavorite.indices) {
                    if (commentAndFavorite[i].is_favorite)
                        notRandom.add(commentAndFavorite[i].pokemon.id)
                }
                for (i in 1..count) {
                    if (!(notRandom.contains(i)))
                        randomNumbers.add(i)

                }
            }
            return randomNumbers[random.nextInt(randomNumbers.size) - 1]
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
                    if (c.alreadyExists(id)) {
                        return c
                    }
                }
            } catch (e: Exception) {
                Log.d("", e.message.toString())
            }

            return null
        }

        fun save(pokemon: Pokemon, commentAndFavorite: CommentAndFavorite) {
            val commentAndFavorite1Check = findOnId(pokemon.id, localSave!!.getCommentAndFavorites())
            if (commentAndFavorite1Check == null) {
                localSave!!.addToCommentAndFavorites(commentAndFavorite)
            }
            try {
                localSave!!.save()
            } catch (e: Exception) {
                Log.d("comfavsave", e.message.toString())
            }

        }

        fun saveShowDialog(show: Boolean) {
            localSave!!.saveMenu(show)
        }

        fun openShowDialog(): Boolean {
            localSave!!.openMenu()
            return localSave!!.getShowDialog()
        }
    }
}
