package com.example.tomi.databasegoles.Data;

import java.io.Serializable;

/**
 * Created by TOMI on 04/05/2017.
 */

public class Partido implements Serializable {
    public static final String TABLE_NAME = "DBPartidos";
    public static final String KEY_ID ="_id";
    public static final String KEY_AÑO ="año";
    public static final String KEY_RIVAL = "rival";
    public static final String KEY_COMPETENCIA  = "competencia";
    public static final String KEY_GOLES="goles";


    private String _id;
    private String rival;
    private String año;
    private String competencia;
    private String goles;



    public void setRival (String rival){
        this.rival = rival;
    }
    public void setAño (String año) {this.año = año;}
    public void setCompetencia (String competencia){
        this.competencia = competencia;
    }

    public void setGoles(String goles){
        this.goles = goles;
    }
    public void set_id(String _id)  {this._id =_id;}
    public String getRival(){ return rival;}
    public String getAño() {return año;}
    public String get_id() {return _id;}

    public String getCompetencia() {
        return competencia;
    }

    public String getGoles() {
        return goles;
    }

}
