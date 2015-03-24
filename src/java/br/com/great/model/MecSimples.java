/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.model;

import java.sql.Time;
import org.json.JSONObject;

/**
 *
 * @author carleandro
 */
public class MecSimples extends Mecanica{
    private int mecsimples_id, visivel;
    private String tipoMecanica;
    private Time tempo;
    private double latitude, longitude;
    private JSONObject mecanica;
    
    public int getMecsimples_id() {
        return mecsimples_id;
    }

    public void setMecsimples_id(int mecsimples_id) {
        this.mecsimples_id = mecsimples_id;
    }

    public String getTipoMecanica() {
        return tipoMecanica;
    }

    public void setTipoMecanica(String tipoMecanica) {
        this.tipoMecanica = tipoMecanica;
    }

    public Time getTempo() {
        return tempo;
    }

    public void setTempo(Time tempo) {
        this.tempo = tempo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getVisivel() {
        return visivel;
    }

    public void setVisivel(int visivel) {
        this.visivel = visivel;
    }

    public JSONObject getMecanica() {
        return mecanica;
    }

    public void setMecanica(JSONObject mecanica) {
        this.mecanica = mecanica;
    }

    
}
