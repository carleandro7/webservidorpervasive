/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.model;

import java.util.List;

/**
 *
 * @author carleandro
 */
public class Mecanica {
    protected int tipo,mecanica_id,missoes_id;
    protected String nome;
    protected List<Integer> requisitos;

    public int getMecanica_id() {
        return mecanica_id;
    }

    public void setMecanica_id(int mecanica_id) {
        this.mecanica_id = mecanica_id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMissoes_id() {
        return missoes_id;
    }

    public void setMissoes_id(int missoes_id) {
        this.missoes_id = missoes_id;
    }

    public List<Integer> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(List<Integer> requisitos) {
        this.requisitos = requisitos;
    }
    
    
    
}
