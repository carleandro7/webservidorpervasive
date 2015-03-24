/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.management;

import br.com.great.controller.MecanicaController;
import br.com.great.controller.MissoesController;
import br.com.great.model.MecSimples;
import br.com.great.model.Missao;
import br.com.great.helpful.OperacoesJSON;
import br.com.great.model.Mecanica;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author carleandro
 */
public class EstadoMissao {

    private Missao missao;
    private int status = 0;
    private ArrayList<EstadoMecanica> listMecanicas;

    public void inicializaMissoesGrupos() {
        if (this.missao != null) {
            ArrayList<Mecanica> list = new MissoesController().getMissaoMecanicas(this.missao.getId());
            listMecanicas = new ArrayList<EstadoMecanica>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getTipo() != 2) {
                    EstadoMecanica estMecanicas = new EstadoMecanica();
                    if (list.get(i).getTipo() == 0) {
                        estMecanicas.setMecSimples(new MecanicaController().
                                getMecSimplesMissao(list.get(i).getMecanica_id(), list.get(i).getMissoes_id()));
                    } else if (list.get(i).getTipo() == 1) {
                        estMecanicas.setMecComposta(new MecanicaController().
                                getMecCompostaMissao(list.get(i).getMecanica_id(), list.get(i).getMissoes_id()));
                    }
                    estMecanicas.setTipo(list.get(i).getTipo());
                    listMecanicas.add(estMecanicas);
                }
            }
        }
    }

    public Missao getMissao() {
        return missao;
    }

    public JSONObject getMissaoJsonArray() {
        String[] key = {"id", "ordem", "grupo_id", "nome", "latitude", "longitude"};
        String[] value = {String.valueOf(missao.getId()), String.valueOf(missao.getOrdem()),
            String.valueOf(missao.getGrupo_id()), missao.getNome(), String.valueOf(missao.getLatitude()), String.valueOf(missao.getLongitude())};
        return new OperacoesJSON().toJSONObject(key, value);
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    public ArrayList<EstadoMecanica> getListMecanicas() {
        return listMecanicas;
    }

    public void setListMecanicas(ArrayList<EstadoMecanica> listMecanicas) {
        this.listMecanicas = listMecanicas;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
