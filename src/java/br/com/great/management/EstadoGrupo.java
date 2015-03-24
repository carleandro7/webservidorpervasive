/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.management;

import br.com.great.controller.GruposController;
import br.com.great.controller.JogadoresController;
import br.com.great.controller.MissoesController;
import br.com.great.model.Grupo;
import br.com.great.model.Jogador;
import br.com.great.model.Missao;
import br.com.great.helpful.Constants;
import br.com.great.helpful.OperacoesJSON;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

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
                    JSONObject jobj = json.getJSONObject(0);
                    jsonResult = setJogadorGrupo(jobj.getInt("jogador_id"));
                    break;
                case Constants.GRUPO_LISTAARQUIVOS:
                       jsonResult = jogoTodosArquivos(json);
                       
                    break;
                case Constants.GRUPO_MECANICAATUAL :
    
                    break;
                case Constants.GRUPO_SETSTATUSMECANICA :
    
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
        boolean result=false;
        int cadastrado = new GruposController().setGrupoParticipando(grupo.getJogo_id(), grupo.getId(), jogador_id);
        //jogador ainda não cadastrado
        if(cadastrado == 1){    
            EstadoJogador estJogador = new EstadoJogador();
            Jogador jogador = new JogadoresController().getJogador(jogador_id);
            estJogador.setJogador(jogador);
            listJogadores.add(estJogador);
            result = true;
        }else if(cadastrado == 2){
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
      if(tipoOrdem == 0){
        return  new JogadoresController().
                               getTodosArquivos(grupo.getId(), 
                                   new OperacoesJSON().toJSONObject(json, 0, "latitude"),
                                   new OperacoesJSON().toJSONObject(json, 0, "longitude"));
      }else{
          return  new JogadoresController().getTodosArquivos(grupo.getId());
      }
    }

    private void enviarMsgJogador(String mensagem) {
      for(int i=0; i< listJogadores.size(); i++){
          listJogadores.get(i).getMensagens().add(mensagem);
      }
        System.err.println("Mensagem "+listJogadores.get(0).getMensagens().get(0));
    }

    private Jogador getJogador(int jogador_id) {
        for(int i=0; i< listJogadores.size(); i++){
          if(listJogadores.get(i).getJogador().getId() == jogador_id)
              return listJogadores.get(i).getJogador();
      }
        return null;
    }

    private JSONArray setJogadorLocalizacao(JSONArray json) {
      int jogador_id = Integer.valueOf(new OperacoesJSON().toJSONObject(json, 0, "jogador_id"));
      boolean result = false;
      Map<String, String> params = new HashMap<String, String>();
      for(int i=0; i< listJogadores.size(); i++){
          if(listJogadores.get(i).getJogador().getId() == jogador_id){
              listJogadores.get(i).getJogador().setLatitude(Double.valueOf(new OperacoesJSON().toJSONObject(json, 0, "latitude")));
              listJogadores.get(i).getJogador().setLongitude(Double.valueOf(new OperacoesJSON().toJSONObject(json, 0, "longitude")));
              listJogadores.get(i).setAtualizarLocalizacao(1);
              System.err.println("teste longitude:"+listJogadores.get(i).getJogador().getLongitude());
              System.err.println("teste latitude:"+listJogadores.get(i).getJogador().getLatitude());
              result = true;
              break;
          }
      }
        String[][] key = {{"result"}};
        String[][] value = {{String.valueOf(result)}};
        return new OperacoesJSON().toJSONArray(key, value);
    }

}
