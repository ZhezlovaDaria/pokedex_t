package com.example.pocedex;


import com.google.gson.annotations.SerializedName;

public class Pokemon {

    public int id;
    public boolean is_default;
    private String name;
    private String link;
    public ability[] abilities;
    public form[] forms;
    public game_indice[] game_indices;
    public String height;
    public String[] held_item;
    public move[] moves;
    public sprite sprites;
    public stat[] stats;
    public type[] types;
    public String weight;
    public String order;
    public String location_area_encounters;

    public void setName(String name){this.name=name;}
    public String getName() {return name;}
    public void setLink(String link){this.link=link;}
    public String getLink() {return link;}


    public class ability
    {
        public Ab ability;
        public boolean is_hidden;
        public int slot;
        public class Ab
        {
            public String name;
            public String url;
        }
    }

    public class form
    {
        public String name;
        public String url;
    }
    public class game_indice
    {
        public int game_index;
        public Version version;
        public class Version {
            public String name;
            public String url;
        }
    }
    public class move
    {
        public String name;
        public String url;
    }
    public class sprite
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
    public class stat
    {
        public int base_stat;
        public int effot;
        public String name;
        public String url;
    }
    public class type
    {
        public int slot;
        public String name;
        public String url;
    }
}

