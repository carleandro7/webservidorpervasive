/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.management;

import br.com.great.model.MecComposta;
import br.com.great.model.MecSimples;
import org.json.JSONArray;

/**
 *
 * @author carleandro
 */
public class EstadoMecanica {
    private MecSimples mecSimples;
    private MecComposta mecComposta;
    private int status = 0;
    private int tipo;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MecSimples getMecSimples() {
        return mecSimples;
    }

    public void setMecSimples(MecSimples mecSimples) {
        this.mecSimples = mecSimples;
    }

    public MecComposta getMecComposta() {
        return mecComposta;
    }

    public void setMecComposta(MecComposta mecComposta) {
        this.mecComposta = mecComposta;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    
}
