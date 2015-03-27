/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.gerenciamento;

import br.com.great.controller.JogadoresController;
import br.com.great.model.Jogador;
import br.com.great.helpful.Constants;
import br.com.great.helpful.OperacoesJSON;
import java.util.ArrayList;
import org.json.JSONArray;

/**
 *
 * @author carleandro
 */
public class GerenciadoGrupo {

    public void inicializaJogadoresGrupos(EstadoGrupo estGrupo) {
        if (estGrupo.getGrupo() != null) {
            ArrayList<Jogador> jogadores = new JogadoresController().getJogadores(estGrupo.getGrupo().getId());
            for (Jogador jogador : jogadores) {
                EstadoJogador estJogador = new EstadoJogador();
                estJogador.setJogador(jogador);
                estGrupo.getListJogadores().add(estJogador);
            }
        }
    }

    public int getJogadorParticipando(EstadoGrupo estGrupo, int jogador_id) {
        if (estGrupo.getListJogadores() != null) {
            for (EstadoJogador estJogador : estGrupo.getListJogadores()) {
                if (estJogador.getJogador().getId() == jogador_id) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public JSONArray acao(EstadoGrupo estGrupo, int acao, JSONArray json) {
        JSONArray jsonResult = null;
        try {
            switch (acao) {
                case Constants.GRUPO_INSERIRPARTICIPANTE:
                    jsonResult = setJogadorGrupo(estGrupo, json.getJSONObject(0).getInt("jogador_id"));
                    break;
                case Constants.GRUPO_LISTAARQUIVOS:
                    jsonResult = jogoTodosArquivos(estGrupo, json);
                    break;
                case Constants.GRUPO_MECANICAATUAL:
                    jsonResult = estGrupo.listarMecanicasVisiveis(json);
                    break;
                case Constants.GRUPO_SETSTATUSMECANICA:

                    break;
                case Constants.GRUPO_GETMECLIBERADA:
                    jsonResult = estGrupo.getMecanicaLiberada(json.getJSONObject(0).getInt("mecanica_id"));
                    break;
                default:
                //comandos caso nenhuma das opções anteriores tenha sido escolhida
            }
        } catch (Exception ex) {
            System.err.println("Erro em acao Estado grupo:" + ex.getMessage());
        }
        return jsonResult;
    }

    private JSONArray setJogadorGrupo(EstadoGrupo estadoGrupo, int jogador_id) {
        boolean result = false;
        //jogador ainda não cadastrado
        if (getJogadorParticipando(estadoGrupo, jogador_id) == 0) {
            EstadoJogador estJogador = getJogadorLista(jogador_id);
            if (estJogador != null) {
                estadoGrupo.getListJogadores().add(estJogador);
            } else {
                Jogador jogador = new JogadoresController().getJogador(jogador_id);
                if (jogador != null) {
                    estJogador = new EstadoJogador();
                    estJogador.setJogador(jogador);
                    estadoGrupo.getListJogadores().add(estJogador);
                    PlayJogo.getListJogadores().add(estJogador);
                    result = true;
                }
            }
        }
        String[][] key = {{"result"}};
        String[][] value = {{String.valueOf(result)}};
        return new OperacoesJSON().toJSONArray(key, value);
    }

    private EstadoJogador getJogadorLista(int jogador_id) {
        for (EstadoJogador estadoJogador : PlayJogo.getListJogadores()) {
            if (estadoJogador.getJogador().getId() == jogador_id) {
                return estadoJogador;
            }
        }
        return null;
    }

    private JSONArray jogoTodosArquivos(EstadoGrupo estGrupo, JSONArray json) {
        return new JogadoresController().
                getTodosArquivos(estGrupo.getGrupo().getId(),
                        new OperacoesJSON().toJSONObject(json, 0, "latitude"),
                        new OperacoesJSON().toJSONObject(json, 0, "longitude"));
    }

}