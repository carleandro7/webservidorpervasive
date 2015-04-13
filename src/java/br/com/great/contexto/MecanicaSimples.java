package br.com.great.contexto;

import java.sql.Time;
import org.json.JSONObject;

public abstract class MecanicaSimples extends Mecanica {

	private int visivel, mecsimples_id;
        private String tiposimples;
        private Time tempo;

    public int getVisivel() {
        return visivel;
    }

    public void setVisivel(int visivel) {
        this.visivel = visivel;
    }

    public int getMecsimples_id() {
        return mecsimples_id;
    }

    public void setMecsimples_id(int mecsimples_id) {
        this.mecsimples_id = mecsimples_id;
    }

   

    public String getTiposimples() {
        return tiposimples;
    }

    public void setTiposimples(String tiposimples) {
        this.tiposimples = tiposimples;
    }

    public Time getTempo() {
        return tempo;
    }

    public void setTempo(Time tempo) {
        this.tempo = tempo;
    }
         
        

}
