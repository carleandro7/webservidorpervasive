/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.management;

import br.com.great.controller.JogadoresController;
import br.com.great.controller.MissoesController;
import br.com.great.model.Grupo;
import br.com.great.model.Jogador;
import br.com.great.model.Missao;
import br.com.great.helpful.Constants;
import br.com.great.helpful.OperacoesJSON;
import br.com.great.model.Mecanica;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;

/**
 *
 * @author carleandro
 */
public class EstadoGrupo {

    private Grupo grupo;
    private int tipoOrdem;
    private ArrayList<EstadoJogador> listJogadores;
    private ArrayList<EstadoMissao> listMissoes;

    public ArrayList<EstadoJogador> getJogadores() {
        return listJogadores;
    }

    public void setJogadores(ArrayList<EstadoJogador> jogadores) {
        this.listJogadores = jogadores;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void inicializaJogadoresGrupos() {
        if (this.grupo != null) {
            ArrayList<Jogador> jogadores = new JogadoresController().getJogadores(this.grupo.getId());
            listJogadores = new ArrayList<EstadoJogador>();
            for (Jogador jogador : jogadores) {
                EstadoJogador estJogador = new EstadoJogador();
                estJogador.setJogador(jogador);
                listJogadores.add(estJogador);
            }
        }
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

    public JSONArray acao(int acao, JSONArray json) {
        JSONArray jsonResult = null;
        try {
            switch (acao) {
                case Constants.GRUPO_INSERIRPARTICIPANTE:
                    jsonResult = setJogadorGrupo(json.getJSONObject(0).getInt("jogador_id"));
                    break;
                case Constants.GRUPO_LISTAARQUIVOS:
                    jsonResult = jogoTodosArquivos(json);

                    break;
                case Constants.GRUPO_MECANICAATUAL:
                    jsonResult = listarMecanicasVisiveis(json);
                    break;
                case Constants.GRUPO_SETSTATUSMECANICA:

                    break;
                case Constants.GRUPO_GETMECLIBERADA:
                    jsonResult = getMecanicaLiberada(json.getJSONObject(0).getInt("mecanica_id"));
                    break;
                case Constants.JOGADOR_SETLOCALIZACAO:
                    jsonResult = setJogadorLocalizacao(json);
                    break;
                default:
                //comandos caso nenhuma das opções anteriores tenha sido escolhida
            }
        } catch (Exception ex) {
            System.err.println("Erro em acao Estado grupo:" + ex.getMessage());
        }
        return jsonResult;
    }

    private JSONArray setJogadorGrupo(int jogador_id) {
        boolean result = false;
        //jogador ainda não cadastrado
        if (getJogadorParticipando(jogador_id) == 0) {
            EstadoJogador estJogador = new EstadoJogador();
            Jogador jogador = new JogadoresController().getJogador(jogador_id);
            estJogador.setJogador(jogador);
            if (listJogadores == null) {
                listJogadores = new ArrayList<EstadoJogador>();
            }
            listJogadores.add(estJogador);
            result = true;
        }
        String[][] key = {{"result"}};
        String[][] value = {{String.valueOf(result)}};
        return new OperacoesJSON().toJSONArray(key, value);
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

    private JSONArray jogoTodosArquivos(JSONArray json) {
        if (tipoOrdem == 0) {
            return new JogadoresController().
                    getTodosArquivos(grupo.getId(),
                            new OperacoesJSON().toJSONObject(json, 0, "latitude"),
                            new OperacoesJSON().toJSONObject(json, 0, "longitude"));
        } else {
            return new JogadoresController().getTodosArquivos(grupo.getId());
        }
    }

    private void enviarMsgJogador(String mensagem) {
        for (int i = 0; i < listJogadores.size(); i++) {
            listJogadores.get(i).getMensagens().add(mensagem);
        }
        System.err.println("Mensagem " + listJogadores.get(0).getMensagens().get(0));
    }

    private Jogador getJogador(int jogador_id) {
        for (int i = 0; i < listJogadores.size(); i++) {
            if (listJogadores.get(i).getJogador().getId() == jogador_id) {
                return listJogadores.get(i).getJogador();
            }
        }
        return null;
    }

    private JSONArray setJogadorLocalizacao(JSONArray json) {
        int jogador_id = Integer.valueOf(new OperacoesJSON().toJSONObject(json, 0, "jogador_id"));
        boolean result = false;
        Map<String, String> params = new HashMap<String, String>();
        for (int i = 0; i < listJogadores.size(); i++) {
            if (listJogadores.get(i).getJogador().getId() == jogador_id) {
                listJogadores.get(i).getJogador().setLatitude(Double.valueOf(new OperacoesJSON().toJSONObject(json, 0, "latitude")));
                listJogadores.get(i).getJogador().setLongitude(Double.valueOf(new OperacoesJSON().toJSONObject(json, 0, "longitude")));
                listJogadores.get(i).setAtualizarLocalizacao(1);
                result = true;
                break;
            }
        }
        String[][] key = {{"result"}};
        String[][] value = {{String.valueOf(result)}};
        return new OperacoesJSON().toJSONArray(key, value);
    }

    private JSONArray listarMecanicasVisiveis(JSONArray json) {
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

    private JSONArray getMecanicaLiberada(int mecanica_id) {
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
