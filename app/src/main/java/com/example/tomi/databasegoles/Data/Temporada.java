package com.example.tomi.databasegoles.Data;

import java.io.Serializable;

/**
 * Created by TOMI on 03/05/2017.
 */

public class Temporada implements Serializable {

    public static final String TABLE_NAME = "DBTemporadas";
    public static final String KEY_ID ="_id";
    public static final String KEY_AÑO = "año";
    public static final String KEY_TOTAL_GOLES  = "totalgoles";
    public static final String KEY_TOTAL_PARTIDOS="totalpartidos";
    public static final String KEY_META="meta";

    private String _id;
    private String año;
    private String totalgoles;
    private String totalpartidos;
    private String meta;


    public void setAño (String año){
        this.año = año;
    }
    public void setTotalgoles (String totalgoles){
        this.totalgoles = totalgoles;
    }

    public void setTotalpartidos(String totalpartidos){
        this.totalpartidos = totalpartidos;
    }
    public void set_id(String _id)  {this._id =_id;}
    public void setMeta(String meta){
        this.meta = meta;
    }
    public String getAño(){ return año;}
    public String get_id() {return _id;}

    public String getTotalgoles() {
        return totalgoles;
    }

    public String getTotalpartidos() {
        return totalpartidos;
    }

    public String getMeta() {
        return meta;
    }


}
