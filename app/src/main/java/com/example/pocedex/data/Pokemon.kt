package com.example.pocedex.data

import com.google.gson.annotations.SerializedName

data class Pokemon(var id: Int = 0, var name: String? = null, var url: String? = null, var base_experience: Int = 0, var abilities: List<Ability>? = null,
                   var forms: List<Form>? = null, var game_indices: List<GameIndice>? = null, var height: String? = null, var held_items: List<HeldItem>? = null,
                   var moves: List<Move>? = null, var species: Specie? = null, var sprites: Sprite? = null, var stats: List<Stat>? = null,
                   var types: List<Type>? = null, var weight: String? = null, var order: String? = null) {

    var safeId: Int = id
        get () {
            if (id == 0) {
                val stringId = url!!.substring(url!!.lastIndexOf("pokemon/") + 8, url!!.length - 1)
                id = Integer.parseInt(stringId) - 1
            }
            return id
        }
        set(value) {
            field = value
        }

    fun getAbilityString(): String {
        if (abilities!!.size == 0)
            return "None"
        var t = ""
        for (i in abilities!!.indices) {
            t += abilities!![i].ability!!.name
            if (i != abilities!!.size - 1)
                t += ", "
        }
        return t
    }

    fun getFormsString(): String {
        if (forms!!.size == 0)
            return "None"
        var t = ""
        for (i in forms!!.indices) {
            t += forms!![i].name
            if (i != forms!!.size - 1)
                t += "\n"
        }
        return t
    }

    fun getgetGameIndiceString(): String {
        if (game_indices!!.size == 0)
            return "None"
        var t = ""
        for (i in game_indices!!.indices) {
            t += game_indices!![i].version!!.name
            if (i != game_indices!!.size - 1)
                t += ", "
        }
        return t
    }
    fun getHeldItemsString(): String {
        if (held_items!!.size == 0)
            return "None"
        var t = ""
        for (i in held_items!!.indices) {
            t += held_items!![i].item!!.name
            if (i != held_items!!.size - 1)
                t += ", "
        }
        return t
    }

    fun getMovesString(): String {
        if (moves!!.size == 0)
            return "None"
        var t = ""
        for (i in moves!!.indices) {
            t += moves!![i].move!!.name
            if (i != moves!!.size - 1)
                t += ", "
        }
        return t
    }

    fun getStats(num: Int): Int {
        return stats!![num].base_stat
    }

    fun getTypesString(): String {
        if (types!!.size == 0)
            return "None"
        var t = ""
        for (i in types!!.indices) {
            t += types!![i].type!!.name
            if (i != types!!.size - 1)
                t += "\n"
        }
        return t
    }

    fun getSpecies(): String? {
        return species!!.name
    }

    fun setSprite(sp: String, num: Int) {
        when (num) {
            0 -> {
                sprites?.other!!.official_artwork!!.front_default = sp
            }
            1 -> {
                sprites?.back_default = sp
            }
            2 -> {
                sprites?.front_default = sp
            }
            3 -> {
                sprites?.back_shiny = sp
            }
            4 -> {
                sprites?.front_shiny = sp
            }
            5 -> {
                sprites?.back_female = sp
            }
            6 -> {
                sprites?.front_female = sp
            }
            7 -> {
                sprites?.back_shiny_female = sp
            }
            8 -> sprites?.front_shiny_female = sp
        }
    }

    fun getSprite(num: Int): String? {
        when (num) {
            0 -> return sprites?.other!!.official_artwork!!.front_default
            1 -> return sprites?.back_default
            2 -> return sprites?.front_default
            3 -> return sprites?.back_shiny
            4 -> return sprites?.front_shiny
            5 -> return sprites?.back_female
            6 -> return sprites?.front_female
            7 -> return sprites?.back_shiny_female
            8 -> return sprites?.front_shiny_female
        }
        return null
    }

    fun getSpriteForList(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + (safeId + 1) + ".png"
    }


    inner class Ability {
        var ability: Abil? = null
        var is_hidden: Boolean = false
        var slot: Int = 0

        inner class Abil {
            var name: String? = null
            var url: String? = null
        }
    }

    inner class Form {
        var name: String? = null
        var url: String? = null
    }

    inner class GameIndice {
        var game_index: Int = 0
        var version: Version? = null

        inner class Version {
            var name: String? = null
            var url: String? = null
        }
    }

    inner class Move {
        var move: Mov? = null

        inner class Mov {
            var name: String? = null
            var url: String? = null
        }
    }

    inner class HeldItem {
        var item: Item? = null

        inner class Item {
            var name: String? = null
            var url: String? = null
        }
    }

    inner class Sprite {
        var back_default: String? = null
        var back_female: String? = null
        var back_shiny: String? = null
        var back_shiny_female: String? = null
        var front_default: String? = null
        var front_female: String? = null
        var front_shiny: String? = null
        var front_shiny_female: String? = null
        var other: Other? = null

        inner class Other {
            var dream_world: dw? = null
            @SerializedName("official-artwork")
            var official_artwork: ow? = null

            inner class dw {
                var front_default: String? = null
                var front_female: String? = null
            }

            inner class ow {
                var front_default: String? = null
            }
        }
    }

    inner class Specie {
        var name: String? = null
        var url: String? = null
    }

    inner class Stat {
        var base_stat: Int = 0
        var effot: Int = 0
        var name: String? = null
        var url: String? = null
    }

    inner class Type {
        var slot: Int = 0
        var type: Typ? = null

        inner class Typ {
            var name: String? = null
            var url: String? = null

        }
    }
}

