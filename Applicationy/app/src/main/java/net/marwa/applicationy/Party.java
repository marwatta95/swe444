package net.marwa.applicationy;

import android.provider.ContactsContract;


public class Party {

    Hall hall;
    Decor decor;
    Band  band;
    Clown clown;
    Custom custom;
    Dj dj;
    Food food;
    Hair hair;
    Photographer photo;
    MakeUp makeup;
    Singer singer;

    public Party(Hall hall, Decor decor, Band band, Clown clown, Custom custom, Dj dj, Food food, Hair hair, Photographer photo,MakeUp makeup,Singer singer){

        this.hall=hall;
        this.decor=decor;
        this.band=band;
        this.clown= clown;
        this.custom= custom;
        this.dj=dj;
        this.food= food;
        this.hair= hair;
        this.photo= photo;
        this.makeup=makeup;
        this.singer=singer;

    }

}
