/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.management;

import br.com.great.controller.JogosController;
import br.com.great.helpful.Constants;
import br.com.great.model.Jogo;
import static br.com.great.helpful.Constants.JOGO_LISTAEXECUTANDO;
import static br.com.great.helpful.Constants.JOGO_LISTAGRUPOS;
import static br.com.great.helpful.Constants.JOGO_NEWJOGO;
import br.com.great.helpful.OperacoesJSON;
import br.com.great.model.Grupo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author carleandro
 */
public class GerenciamentoJogos extends Thread {
    public ArrayList<EstadoJogo> lisJogosExe = new ArrayList<EstadoJogo>();
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
                enviarLocalizacao();
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GerenciamentoJogos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public JSONArray acao(int acao, int jogo_id, JSONArray json) {
        JSONArray jsonResult = null;
        JSONObject jobj;
        try {
            switch (acao) {
                case JOGO_NEWJOGO:
                    jobj = json.getJSONObject(0);
                    jsonResult = newJogo(jobj.getInt("jogo_id"), Integer.valueOf(jobj.getString("jogador_id")), jobj.getString("nomeficticio"));
                    break;
                case JOGO_LISTAGRUPOS:
                    jsonResult = listGrupos(jogo_id);
                    break;
                case JOGO_LISTAEXECUTANDO:
                    jsonResult = listJogosExecutando(jogo_id, Integer.valueOf(new OperacoesJSON().toJSONObject(json, 0, "jogador_id")));
                    break;

                default:
                //comandos caso nenhuma das opções anteriores tenha sido escolhida
            }
        } catch (JSONException ex) {
            System.err.println("Erro em acao:" + ex.getMessage());
        }
        return jsonResult;
    }

    public JSONArray acaoGrupo(int acao, int grupo_id, int jogo_id, JSONArray json) {
        JSONArray jsonResult = null;
        for (int i = 0; i < lisJogosExe.size(); i++) {
            if (lisJogosExe.get(i).getJogo().getJogoExe_id() == jogo_id) {
                for (int j = 0; j < lisJogosExe.get(i).getListGrupo().size(); j++) {
                    if (lisJogosExe.get(i).getListGrupo().get(j).getGrupo().getId() == grupo_id) {
                        jsonResult = lisJogosExe.get(i).getListGrupo().get(j).acao(acao, json);
                    }
                }
            }
        }
        return jsonResult;
    }

    public boolean stopJogos() {
        return true;
    }

    private JSONArray newJogo(int jogo_id, int jogador_id, String nome) throws JSONException {
        EstadoJogo estJogo = new EstadoJogo();
        Jogo jogo = new JogosController().getJogo(jogo_id);
        jogo.setNomeficticio(nome);
        jogo.setJogador_id(jogador_id);
        jogo.setJogoExe_id(++id_jogo_exe);
        estJogo.setJogo(jogo);
        estJogo.iniciaConfiguracoes();
        lisJogosExe.add(estJogo);
        return new JSONArray().put(new JSONObject().put("return", "true"));
        
    }

    private JSONArray listGrupos(int jogo_id) {
        for (EstadoJogo estJogo : lisJogosExe) {
            if (estJogo.getJogo().getJogoExe_id() == jogo_id) {
                return estJogo.getGrupos();
            }
        }
        return null;
    }

    private JSONArray listJogosExecutando(int jogo_id, int jogador_id) {
        JSONArray json = new JSONArray();
        try {
            for (EstadoJogo estJogo : lisJogosExe) {
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

    private void enviarLocalizacao() {
        
        for (int i = 0; i < lisJogosExe.size(); i++) {
            if(alterouLocalizacaoJogador(i)){
                alterouLocalizacaoJogadorJson(i, lisJogosExe.get(i).getJogo());
            }
        }
    }

    private boolean alterouLocalizacaoJogador(int jogo_id) {
        for (int j = 0; j < lisJogosExe.get(jogo_id).getListGrupo().size(); j++) {
            for (int h = 0; h < lisJogosExe.get(jogo_id).getListGrupo().get(j).getListJogadores().size(); h++) {
                if (lisJogosExe.get(jogo_id).getListGrupo().get(j).getListJogadores().get(h).getAtualizarLocalizacao() == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private void alterouLocalizacaoJogadorJson(int jogo_posicao, Jogo jogo) {
       try{
        JSONArray jogoJson = new JSONArray();
        for (int j = 0; j < lisJogosExe.get(jogo_posicao).getListGrupo().size(); j++) {
            JSONObject grupo = new JSONObject();
            String[] key = {"grupo_id", "nome"};
            String[] value = {String.valueOf(lisJogosExe.get(jogo_posicao).getListGrupo().get(j).getGrupo().getId()),
                lisJogosExe.get(jogo_posicao).getListGrupo().get(j).getGrupo().getNome()};
            grupo.put("grupo",new OperacoesJSON().toJSONObject(key, value));
            
            List<JSONObject> listJogador = new ArrayList<JSONObject>();
            for (int h = 0; h < lisJogosExe.get(jogo_posicao).getListGrupo().get(j).getListJogadores().size(); h++) {
                String[] keyJogador = {"id", "nome", "latitude", "longitude"};
                String[] valueJogador = {
                    String.valueOf(lisJogosExe.get(jogo_posicao).getListGrupo().get(j).getListJogadores().get(h).getJogador().getId()),
                    lisJogosExe.get(jogo_posicao).getListGrupo().get(j).getListJogadores().get(h).getJogador().getEmail(),
                    String.valueOf(lisJogosExe.get(jogo_posicao).getListGrupo().get(j).getListJogadores().get(h).getJogador().getLatitude()),
                    String.valueOf(lisJogosExe.get(jogo_posicao).getListGrupo().get(j).getListJogadores().get(h).getJogador().getLongitude())
                };
                lisJogosExe.get(jogo_posicao).getListGrupo().get(j).getListJogadores().get(h).setAtualizarLocalizacao(0);    
                listJogador.add(new OperacoesJSON().toJSONObject(keyJogador, valueJogador));
            }
            grupo.put("jogadores", listJogador);
            jogoJson.put(grupo);
        }
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("user", "root");
        params.put("tipoacao", "atualizaLocalizacao");
        params.put("localizacao", jogoJson.toString());
           System.err.println("Json "+ jogoJson.toString());
        new JogosController().enviarMensagem(jogo.getId(), params);
       }catch(JSONException ex){
           System.err.println("Erro msg"+ex);
       }
    }
}
