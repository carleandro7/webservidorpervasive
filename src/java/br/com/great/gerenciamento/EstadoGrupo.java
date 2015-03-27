/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.gerenciamento;


import br.com.great.controller.MissoesController;
import br.com.great.model.Grupo;
import br.com.great.model.Mecanica;
import br.com.great.model.Missao;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;


/**
 *
 * @author carleandro
 */
public class EstadoGrupo {    
    private Grupo grupo;
    private ArrayList<EstadoJogador> listJogadores = new ArrayList<EstadoJogador>();
    private ArrayList<EstadoMissao> listMissoes;

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public ArrayList<EstadoJogador> getListJogadores() {
        return listJogadores;
    }

    public void setListJogadores(ArrayList<EstadoJogador> listJogadores) {
        this.listJogadores = listJogadores;
    }

    public ArrayList<EstadoMissao> getListMissoes() {
        return listMissoes;
    }

    public void setListMissoes(ArrayList<EstadoMissao> listMissoes) {
        this.listMissoes = listMissoes;
    }
    
    public void inicializaMissoesGrupos() {
        if (this.grupo != null) {
            ArrayList<Missao> missoes = new MissoesController().getMissoesGrupo(this.grupo.getId());
            listMissoes = new ArrayList<EstadoMissao>();
            for (Missao missao : missoes) {
                EstadoMissao estMissao = new EstadoMissao();
                estMissao.setMissao(missao);
                estMissao.inicializaMissoesGrupos();
                listMissoes.add(estMissao);
            }
        }
    }
    
    public int getJogadorParticipando(int jogador_id) {
        if (listJogadores != null) {
            for (EstadoJogador estJogador : listJogadores) {
                if (estJogador.getJogador().getId() == jogador_id) {
                    return 1;
                }
            }
        }
        return 0;
    }
    
    public JSONArray listarMecanicasVisiveis(JSONArray json) {
        JSONArray jsonResult = new JSONArray();
        for (int i = 0; i < listMissoes.size(); i++) {
            for (int j = 0; j < listMissoes.get(i).getListMecanicas().size(); j++) {
                if (listMissoes.get(i).getListMecanicas().get(j).getTipo() == 0) {
                    if (listMissoes.get(i).getListMecanicas().get(j).getMecSimples().getVisivel() == 1) {
                        jsonResult.put(listMissoes.get(i).getListMecanicas().get(j).getMecSimples().getMecSimplesJSON());
                    }
                } else if (listMissoes.get(i).getListMecanicas().get(j).getTipo() == 1) {
                    for (int h = 0; h < listMissoes.get(i).getListMecanicas()
                            .get(j).getMecComposta().getListMecSimples().size(); h++) {
                        if (listMissoes.get(i).getListMecanicas()
                                .get(j).getMecComposta().getListMecSimples().get(h).getVisivel() == 1) {
                            jsonResult.put(listMissoes.get(i).getListMecanicas()
                                    .get(j).getMecComposta().getListMecSimples().get(h).getMecSimplesJSON());
                        }
                    }

                }
            }
        }
        return jsonResult;
    }

    public JSONArray getMecanicaLiberada(int mecanica_id) {
        JSONArray jsonResult = new JSONArray();
        VerificaReqMecanica verifica = new VerificaReqMecanica(listMissoes);
        Mecanica mecanica = verifica.getMecanicaAll(mecanica_id);
        if (mecanica != null && !mecanica.getRequisitos().isEmpty()) {
            //existe e tem requisitos
            List<String> requisitos = new ArrayList<String>();
            for (int i = 0; i < mecanica.getRequisitos().size(); i++) {
                if(verifica.getMecanicaAll(mecanica.getRequisitos().get(i)) != null){
                    requisitos.add(verifica.getMecanicaAll(mecanica.getRequisitos().get(i)).getNome());
                }
            }
            jsonResult.put(requisitos);
        } else if (mecanica != null) {
            //existe mais nao tem requisitos
        }
        return jsonResult;
    }

   
}
