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
public class MecComposta extends Mecanica{
    private int meccomposta_id;
    private List<MecSimples> ListMecSimples; 

    public int getMeccomposta_id() {
        return meccomposta_id;
    }

    public void setMeccomposta_id(int meccomposta_id) {
        this.meccomposta_id = meccomposta_id;
    }

    public List<MecSimples> getListMecSimples() {
        return ListMecSimples;
    }

    public void setListMecSimples(List<MecSimples> ListMecSimples) {
        this.ListMecSimples = ListMecSimples;
    }

}
