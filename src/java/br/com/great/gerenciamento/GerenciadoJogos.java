/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.gerenciamento;

import br.com.great.controller.JogadoresController;
import br.com.great.controller.JogosController;
import br.com.great.util.Constants;
import br.com.great.model.Jogo;
import br.com.great.util.OperacoesJSON;
import br.com.great.model.Grupo;
import br.com.great.model.Jogador;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author carleandro
 */
public class GerenciadoJogos extends Thread {
    private ArrayList<EstadoJogo> lisJogos = PlayJogo.getListJogos();
    private GerenciadoGrupo gerGrupo = new GerenciadoGrupo();
    private GerenciadoJogador gerJogador = new GerenciadoJogador();
    private int acao = 0;
    private int id_jogo_exe=0;
    @Override
    public void run() {
        while (!isInterrupted()) {
            switch (acao) {
                case Constants.JOGADOR_ENVIARLOCALIZACAO:
                    //inicia um novo jogo
                    //newJogo();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                default:
                //comandos caso nenhuma das opções anteriores tenha sido escolhida
                }
            try {
   //             enviarLocalizacao();
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GerenciadoJogos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public JSONArray acao(int acao, int jogo_id, JSONArray json) {
        JSONArray jsonResult = null;
        JSONObject jobj;
        try {
            switch (acao) {
                case Constants.JOGO_NEWJOGO:
                    jobj = json.getJSONObject(0);
                    jsonResult = newJogo(jobj.getInt("jogo_id"), Integer.valueOf(jobj.getString("jogador_id")), jobj.getString("nomeficticio"));
                    break;
                case Constants.JOGO_LISTAGRUPOS:
                    jsonResult = listGrupos(jogo_id);
                    break;
                case Constants.JOGO_LISTAEXECUTANDO:
                    jsonResult = listJogosExecutando(jogo_id, Integer.valueOf(new OperacoesJSON().toJSONObject(json, 0, "jogador_id")));
                    break;
                case Constants.JOGADOR_CADJOGADOR:
                    jsonResult = gerJogador.cadastrarJogador(new OperacoesJSON().toJSONObject(json, 0, "email"), new OperacoesJSON().toJSONObject(json, 0, "password"));
                    break;
                case Constants.JOGADOR_REGDISPOSITIVO:
                    int jogador_id = Integer.valueOf(new OperacoesJSON().toJSONObject(json, 0, "jogador_id"));
                    jsonResult = gerJogador.registrarDevice(jogador_id, new OperacoesJSON().toJSONObject(json, 0, "device_id"));
                    break;
                case Constants.JOGADOR_SETLOCALIZACAO:
                    jsonResult = gerJogador.setJogadorLocalizacao(json);
                    break;
                  case Constants.GRUPO_LOCALIZACAOJOGADORES:
                    jsonResult = enviarLocalizacao(jogo_id, acao);
                    break;    
                default:
                //comandos caso nenhuma das opções anteriores tenha sido escolhida
            }
        } catch (JSONException ex) {
            System.err.println("Erro em acao:" + ex.getMessage());
        }
        return jsonResult;
    }
    
    public void carregaJogadores(){
       ArrayList<Jogador> jogadores = new JogadoresController().getJogadoresAll();
       ArrayList<EstadoJogador> listJogadores = PlayJogo.getListJogadores();
       for(Jogador jogador:jogadores){
           EstadoJogador estJogador = new EstadoJogador();
           estJogador.setJogador(jogador);
           listJogadores.add(estJogador);
       }
    }
    public JSONArray acaoGrupo(int acao, int grupo_id, int jogo_id, JSONArray json) {
       for(int i=0;i<lisJogos.size();i++){
           if(lisJogos.get(i).getJogo().getJogoExe_id() == jogo_id){
               for(int j=0;j<lisJogos.get(i).getListGrupo().size();j++){
                   if(lisJogos.get(i).getListGrupo().get(j).getGrupo().getId() == grupo_id){
                    return gerGrupo.acao(lisJogos.get(i).getListGrupo().get(j), acao, json);
                   }
               }
           }
       }
        return null;
    }


    private JSONArray newJogo(int jogo_id, int jogador_id, String nome) throws JSONException {
        EstadoJogo estJogo = new EstadoJogo();
        Jogo jogo = new JogosController().getJogo(jogo_id);
        jogo.setNomeficticio(nome);
        jogo.setJogador_id(jogador_id);
        jogo.setJogoExe_id(++id_jogo_exe);
        estJogo.setJogo(jogo);
        estJogo.iniciaConfiguracoes();
        lisJogos.add(estJogo);
        return new JSONArray().put(new JSONObject().put("return", "true"));
    }

    private JSONArray listGrupos(int jogo_id) {
        for (EstadoJogo estJogo : lisJogos) {
            if (estJogo.getJogo().getJogoExe_id() == jogo_id) {
                return estJogo.getGrupos();
            }
        }
        return null;
    }

    private JSONArray listJogosExecutando(int jogo_id, int jogador_id) {
        JSONArray json = new JSONArray();
        try {
            for (EstadoJogo estJogo : lisJogos) {
                if (estJogo.getJogo().getId() == jogo_id) {
                    JSONObject jobj = new JSONObject();
                    jobj.put("id", estJogo.getJogo().getId());
                    jobj.put("nome", estJogo.getJogo().getNome());
                    jobj.put("icone", estJogo.getJogo().getIcone());
                    jobj.put("nomeficticio", estJogo.getJogo().getNomeficticio());
                    jobj.put("jogoexe_id", estJogo.getJogo().getJogoExe_id());
                    Grupo grupo = estJogo.grupoJogadorParticipando(jogador_id);
                    if (grupo != null) {
                        jobj.put("grupo_id", estJogo.grupoJogadorParticipando(jogador_id).getId());
                        jobj.put("grupo_nome", estJogo.grupoJogadorParticipando(jogador_id).getNome());
                    } else {
                        jobj.put("grupo_id", 0);
                        jobj.put("grupo_nome", "");
                    }
                    json.put(jobj);
                }
            }
        } catch (JSONException ex) {
            System.err.println("Erro listaJogosExecutando:" + ex.getMessage());
        }
        return json;
    }

    private void alterarLocalJogador(int jogador_id, double latitude, double longitude){
        for(int i=0;i<PlayJogo.getListJogadores().size();i++){
            if (PlayJogo.getListJogadores().get(i).getJogador().getId()==jogador_id) {
                PlayJogo.getListJogadores().get(i).getJogador().setLatitude(latitude);
                PlayJogo.getListJogadores().get(i).getJogador().setLongitude(longitude);
                break;
            }
        }
        
    }
    
   private JSONArray enviarLocalizacao(int jogo_id, int acao) {
       JSONArray jogoJson = new JSONArray();
        for(int i=0;i<lisJogos.size();i++){
           if(lisJogos.get(i).getJogo().getJogoExe_id() == jogo_id){
               for(int j=0;j<lisJogos.get(i).getListGrupo().size();j++){
                    gerGrupo.acao(lisJogos.get(i).getListGrupo().get(j), acao, jogoJson);
               }
            }
        }
        return jogoJson;
    }
   
}
