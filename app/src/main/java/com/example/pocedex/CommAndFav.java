package com.example.pocedex;


import java.io.Serializable;

public class CommAndFav implements Serializable {

    public String name;
    public int id;
    public String link;
    public boolean is_fav=false;
    public String comment=null;

    public boolean equals(int id) {
        if (this.id == id) {
            return true;
        }
        return false;
    }

}
