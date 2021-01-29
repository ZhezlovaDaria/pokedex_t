package com.example.pocedex.data

import com.google.gson.annotations.SerializedName

class Pokemon {

    private var id: Int = 0
    private var name: String? = null
    private var url: String? = null
    private var base_experience: Int = 0
    private var abilities: List<Ability>? = null
    private var forms: List<Form>? = null
    private var game_indices: List<GameIndice>? = null
    private var height: String? = null
    private var held_items: List<HeldItem>? = null
    private var moves: List<Move>? = null
    private var species: Specie? = null
    private val sprites = Sprite()
    private var stats: List<Stat>? = null
    private var types: List<Type>? = null
    private var weight: String? = null
    private var order: String? = null

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        if (id == 0) {
            val stringId = url!!.substring(url!!.lastIndexOf("pokemon/") + 8, url!!.length - 1)
            id = Integer.parseInt(stringId) - 1
        }
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

    fun setBE(be: Int) {
        base_experience = be
    }

    fun getBe(): Int {
        return base_experience
    }

    fun setHeight(height: String) {
        this.height = height
    }

    fun getHeight(): String? {
        return height
    }

    fun setWeight(weight: String) {
        this.weight = weight
    }

    fun getWeight(): String? {
        return weight
    }

    fun setOrder(order: String) {
        this.order = order
    }

    fun getOrder(): String? {
        return order
    }

    fun setAbility(ability: List<Ability>) {
        abilities = ability
    }

    fun getAbility(): List<Ability>? {
        return abilities
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

    fun setForms(forms: List<Form>) {
        this.forms = forms
    }

    fun getForms(): List<Form>? {
        return forms
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

    fun setGameIndice(game_indices: List<GameIndice>) {
        this.game_indices = game_indices
    }

    fun getGameIndice(): List<GameIndice>? {
        return game_indices
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

    fun setHeldItems(held_items: List<HeldItem>) {
        this.held_items = held_items
    }

    fun getHeldItems(): List<HeldItem>? {
        return held_items
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

    fun setMoves(moves: List<Move>) {
        this.moves = moves
    }

    fun getMoves(): List<Move>? {
        return moves
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

    fun setStats(stats: List<Stat>) {
        this.stats = stats
    }

    fun getStats(num: Int): Int {
        return stats!![num].base_stat
    }

    fun setTypes(types: List<Type>) {
        this.types = types
    }

    fun getTypes(): List<Type>? {
        return types
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


    fun setSpecies(species: Specie) {
        this.species = species
    }

    fun getSpecies(): String? {
        return species!!.name
    }

    fun setSprite(sp: String, num: Int) {
        when (num) {
            0 -> {
                sprites.other!!.official_artwork!!.front_default = sp
                sprites.back_default = sp
                sprites.front_default = sp
                sprites.back_shiny = sp
                sprites.front_shiny = sp
                sprites.back_female = sp
                sprites.front_female = sp
                sprites.back_shiny_female = sp
                sprites.front_shiny_female = sp
            }
            1 -> {
                sprites.back_default = sp
                sprites.front_default = sp
                sprites.back_shiny = sp
                sprites.front_shiny = sp
                sprites.back_female = sp
                sprites.front_female = sp
                sprites.back_shiny_female = sp
                sprites.front_shiny_female = sp
            }
            2 -> {
                sprites.front_default = sp
                sprites.back_shiny = sp
                sprites.front_shiny = sp
                sprites.back_female = sp
                sprites.front_female = sp
                sprites.back_shiny_female = sp
                sprites.front_shiny_female = sp
            }
            3 -> {
                sprites.back_shiny = sp
                sprites.front_shiny = sp
                sprites.back_female = sp
                sprites.front_female = sp
                sprites.back_shiny_female = sp
                sprites.front_shiny_female = sp
            }
            4 -> {
                sprites.front_shiny = sp
                sprites.back_female = sp
                sprites.front_female = sp
                sprites.back_shiny_female = sp
                sprites.front_shiny_female = sp
            }
            5 -> {
                sprites.back_female = sp
                sprites.front_female = sp
                sprites.back_shiny_female = sp
                sprites.front_shiny_female = sp
            }
            6 -> {
                sprites.front_female = sp
                sprites.back_shiny_female = sp
                sprites.front_shiny_female = sp
            }
            7 -> {
                sprites.back_shiny_female = sp
                sprites.front_shiny_female = sp
            }
            8 -> sprites.front_shiny_female = sp
        }
    }

    fun getSprite(num: Int): String? {
        when (num) {
            0 -> return sprites.other!!.official_artwork!!.front_default
            1 -> return sprites.back_default
            2 -> return sprites.front_default
            3 -> return sprites.back_shiny
            4 -> return sprites.front_shiny
            5 -> return sprites.back_female
            6 -> return sprites.front_female
            7 -> return sprites.back_shiny_female
            8 -> return sprites.front_shiny_female
        }
        return null
    }

    fun getSpriteForList(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + (getId() + 1) + ".png"
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

