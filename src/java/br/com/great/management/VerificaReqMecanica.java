/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.management;

import br.com.great.model.MecComposta;
import br.com.great.model.MecSimples;
import br.com.great.model.Mecanica;
import java.util.ArrayList;

/**
 *
 * @author carleandro
 */
public class VerificaReqMecanica {
    private ArrayList<EstadoMissao> listMissoes;

    public void setListMissoes(ArrayList<EstadoMissao> listMissoes) {
        this.listMissoes = listMissoes;
    }

    public VerificaReqMecanica(ArrayList<EstadoMissao> listMissoes) {
        this.listMissoes = listMissoes;
    }
    public VerificaReqMecanica() {
    }
    
    public Mecanica getMecanicaAll(int mecanica_id){
        Mecanica mecanica = getMecSimplesAll(mecanica_id);
        if (mecanica == null) {
            mecanica = getMecComposta(mecanica_id);
        }
        return mecanica;
        
    }
    public MecSimples getMecSimplesAll(int mecanica_id) {
        MecSimples mecSimples = getMecSimples(mecanica_id);
        if (mecSimples == null) {
            mecSimples = getMecSimpComposta(mecanica_id);
        }
        return mecSimples;
    }

    public MecSimples getMecSimples(int mecanica_id) {
        for (EstadoMissao listMissoe : listMissoes) {
            for (int j = 0; j < listMissoe.getListMecanicas().size(); j++) {
                if (listMissoe.getListMecanicas().get(j).getTipo() == 0 && listMissoe.getListMecanicas().get(j).getMecSimples().getMecanica_id() == mecanica_id) {
                    return listMissoe.getListMecanicas().get(j).getMecSimples();
                }
            }
        }
        return null;
    }

    public MecSimples getMecSimpComposta(int mecanica_id) {
        for (EstadoMissao listMissoe : listMissoes) {
            for (int j = 0; j < listMissoe.getListMecanicas().size(); j++) {
                if (listMissoe.getListMecanicas().get(j).getTipo() == 1) {
                    for (int h = 0; h < listMissoe.getListMecanicas().get(j).getMecComposta().getListMecSimples().size(); h++) {
                        if (listMissoe.getListMecanicas().get(j).getMecComposta().getListMecSimples().get(h).getMecanica_id() == mecanica_id) {
                            return listMissoe.getListMecanicas().get(j).getMecComposta().getListMecSimples().get(h);
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public MecComposta getMecComposta(int mecanica_id) {
        for (EstadoMissao listMissoe : listMissoes) {
            for (int j = 0; j < listMissoe.getListMecanicas().size(); j++) {
                if (listMissoe.getListMecanicas().get(j).getTipo() == 1) {
                    return listMissoe.getListMecanicas().get(j).getMecComposta();
                }
            }
        }
        return null;
    }
    
}
