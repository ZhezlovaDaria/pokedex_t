package com.example.pocedex;

import com.google.gson.annotations.SerializedName;

public class Pokemon {

    private int id;
    private String name;
    private String url;
    private int base_experience;
    private Ability[] abilities;
    private Form[] forms;
    private GameIndice[] game_indices;
    private String height;
    private HeldItem[] held_items;
    private Move[] moves;
    private Specie species;
    private Sprite sprites=new Sprite();
    private Stat[] stats;
    private Type[] types;
    private String weight;
    private String order;

    public void setId(int id){this.id=id;}
    public int getId() {return id;}

    public void setName(String name){this.name=name;}
    public String getName() {return name;}

    public void setUrl(String link){this.url=link;}
    public String getUrl() {return url;}

    public void setBE(int be){base_experience=be;}
    public int getBe() {return base_experience;}

    public void setHeight(String height){this.height=height;}
    public String getHeight() {return height;}

    public void setWeight(String weight){this.weight=weight;}
    public String getWeight() {return weight;}

    public void setOrder(String order){this.order=order;}
    public String getOrder() {return order;}

    public void setAbility(Ability[] ability){abilities=ability;}
    public Ability[] getAbility() {return abilities;}
    public String getAbilityString() {
        if (abilities.length==0)
            return "None";
        String t="";
        for (int i=0; i<abilities.length;i++)
        {
            t+=abilities[i].ability.name;
            if (i!=abilities.length-1)
                t+=", ";
        }
        return t;
    }

    public void setForms(Form[] forms){this.forms=forms;}
    public Form[] getForms() {return forms;}
    public String getFormsString() {
        if (forms.length==0)
            return "None";
        String t="";
        for (int i=0; i<forms.length;i++)
        {
            t+=forms[i].name;
            if (i!=forms.length-1)
                t+="\n";
        }
        return t;
    }

    public void setGameIndice(GameIndice[] game_indices){this.game_indices=game_indices;}
    public GameIndice[] getGameIndice() {return game_indices;}
    public String getgetGameIndiceString() {
        if (game_indices.length==0)
            return "None";
        String t="";
        for (int i=0; i<game_indices.length;i++)
        {
            t+=game_indices[i].version.name;
            if (i!=game_indices.length-1)
                t+=", ";
        }
        return t;
    }

    public void setHeldItems(HeldItem[] held_items){this.held_items=held_items;}
    public HeldItem[] getHeldItems() {return held_items;}
    public String getHeldItemsString() {
        if (held_items.length==0)
            return "None";
        String t="";
        for (int i=0; i<held_items.length;i++)
        {
            t+=held_items[i].item.name;
            if (i!=held_items.length-1)
                t+=", ";
        }
        return t;
    }

    public void setMoves(Move[] moves){this.moves=moves;}
    public Move[] getMoves() {return moves;}
    public String getMovesString() {
        if (moves.length==0)
            return "None";
        String t="";
        for (int i=0; i<moves.length;i++)
        {
            t+=moves[i].move.name;
            if (i!=moves.length-1)
                t+=", ";
        }
        return t;
    }

    public void setStats(Stat[] stats){this.stats=stats;}
    public int getStats(int num) { return stats[num].base_stat;}

    public void setTypes(Type[] types){this.types=types;}
    public Type[] getTypes() {return types;}
    public String getTypesString() {
        if (types.length==0)
            return "None";
        String t="";
        for (int i=0; i<types.length;i++)
        {
            t+=types[i].type.name;
            if (i!=types.length-1)
                t+="\n";
        }
        return t;
    }


    public void setSpecies(Specie species){this.species=species;}
    public String getSpecies() {return species.name;}

    public void setSprite(String sp, int num){
        switch (num) {
            case 0:  sprites.other.official_artwork.front_default=sp;
            case 1:  sprites.back_default=sp;
            case 2:  sprites.front_default=sp;
            case 3:  sprites.back_shiny=sp;
            case 4:  sprites.front_shiny=sp;
            case 5:  sprites.back_female=sp;
            case 6:  sprites.front_female=sp;
            case 7:  sprites.back_shiny_female=sp;
            case 8:  sprites.front_shiny_female=sp;
        }
    }
    public String getSprite(int num)
    {
        switch (num) {
            case 0:  return sprites.other.official_artwork.front_default;
            case 1:  return sprites.back_default;
            case 2:  return sprites.front_default;
            case 3:  return sprites.back_shiny;
            case 4:  return sprites.front_shiny;
            case 5:  return sprites.back_female;
            case 6:  return sprites.front_female;
            case 7:  return sprites.back_shiny_female;
            case 8:  return sprites.front_shiny_female;
    }
    return null;
    }


    private class Ability
    {
        public Abil ability;
        public boolean is_hidden;
        public int slot;
        private class Abil
        {
            public String name;
            public String url;
        }
    }

    private class Form
    {
        public String name;
        public String url;
    }
    private class GameIndice
    {
        public int game_index;
        public Version version;
        private class Version {
            public String name;
            public String url;
        }
    }
    private class Move
    {
        public Mov move;
        public class Mov
        {
            public String name;
            public String url;
        }
    }
    private class HeldItem
    {
        public Item item;
        public class Item
        {
            public String name;
            public String url;
        }
    }
    private class Sprite
    {
        public String back_default;
        public String back_female;
        public String back_shiny;
        public String back_shiny_female;
        public String front_default;
        public String front_female;
        public String front_shiny;
        public String front_shiny_female;
        public Other other;

        public class Other
        {
            public dw dream_world;
            @SerializedName("official-artwork")
            public ow official_artwork;
            public class dw
            {
                public String front_default;
                public String front_female;
            }
            public class ow
            {
                public String front_default;
            }
        }
    }
    private class Specie
    {
        public String name;
        public String url;
    }
    private class Stat
    {
        public int base_stat;
        public int effot;
        public String name;
        public String url;
    }
    private class Type
    {
        public int slot;
        public Typ type;

        private class Typ
        {
            public String name;
            public String url;

        }
    }
}

