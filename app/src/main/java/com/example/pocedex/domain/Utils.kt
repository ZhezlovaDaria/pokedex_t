package com.example.pocedex.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Handler
import android.util.Log
import com.example.pocedex.data.CommentAndFavorite
import com.example.pocedex.data.Pokemon
import com.example.pocedex.presentation.INetworkChange
import java.util.*


internal open class Utils {

    companion object {
        const val preferenses = "commandfav"
        var localSave: LocalSave? = null
        private var randomNumbers: ArrayList<Int> = ArrayList()
        var isConnected: Boolean = false

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

        fun startNetworkCallback(context: Context) {
            val cm: ConnectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder: NetworkRequest.Builder = NetworkRequest.Builder()

            cm.registerNetworkCallback(
                    builder.build(),
                    object : ConnectivityManager.NetworkCallback() {

                        override fun onAvailable(network: android.net.Network) {
                            super.onAvailable(network)
                            isConnected = true
                            var handler: Handler = Handler(context.mainLooper)
                            handler.post { (context as INetworkChange).setOnline() }

                        }

                        override fun onLost(network: android.net.Network) {
                            super.onLost(network)
                            isConnected = false
                        }
                    })
        }

        fun stopNetworkCallback(context: Context) {
            val cm: ConnectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            try {
                cm.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
            } catch (e: IllegalArgumentException) {
                Log.d("NetworkCallback", e.message.toString())
            }
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
