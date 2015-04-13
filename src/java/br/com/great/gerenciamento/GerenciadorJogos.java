/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.gerenciamento;

import br.com.great.contexto.Jogo;
import br.com.great.contexto.Missao;
import br.com.great.contexto.Texto;
import br.com.great.controller.JogadoresController;
import br.com.great.controller.JogosController;
import br.com.great.util.Constants;
import br.com.great.util.OperacoesJSON;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author carleandro
 */
public class GerenciadorJogos extends Thread {

    @Override
    public void run() {
        while (!isInterrupted()) {

            try {
                //             enviarLocalizacao();
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GerenciadorJogos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public JSONArray acao(JSONArray json) {
        JSONArray jsonResult = null;
        try {
            JSONObject jobj;
            int acao = json.getJSONObject(0).getInt("acao");
            switch (acao) {
                case Constants.JOGO_NEWJOGO:
                    jobj = json.getJSONObject(1);
                    jsonResult = newJogo(jobj.getInt("jogo_id"), Integer.valueOf(jobj.getString("jogador_id")), jobj.getString("nomeficticio"));
                    break;
                case Constants.JOGO_LISTAJGO:
                    jobj = json.getJSONObject(1);
                    jsonResult = listarJogos(jobj.getDouble("latitude"), jobj.getDouble("longitude"), jobj.getInt("distancia"));
                    break;
                case Constants.JOGO_CADASTRAR:
                    jobj = json.getJSONObject(1);
                    jsonResult = newJogo(jobj.getInt("jogo_id"), Integer.valueOf(jobj.getString("jogador_id")), jobj.getString("nomeficticio"));
                    break;
                case Constants.JOGADOR_LOGIN:
                    jobj = json.getJSONObject(1);
                    jsonResult = login(jobj.getString("email"), jobj.getString("senha"));
                    break;
                case Constants.JOGADOR_CADASTRAR:
                    jobj = json.getJSONObject(1);
                    jsonResult = cadastrarJogador(jobj.getString("email"), jobj.getString("senha"));
                    break;

                default:
                //comandos caso nenhuma das opções anteriores tenha sido escolhida
            }
        } catch (JSONException ex) {
            System.err.println("Erro em acao:" + ex.getMessage());
        }
        return jsonResult;
    }

    public void carregaJogadores() {
        PlayJogo.setJogadores(new JogadoresController().listarTodos());
    }

    private JSONArray cadastrarJogador(String email, String senha) {
        String[] value, key = {"result"};
        int result = new JogadoresController().cadastrarJogador(email, senha);
        if (result == 0) {
            value = new String[]{"erro ao salvar"};
        } else if (result == -1) {
            value = new String[]{"Jogador já existe"};
        } else {
            value = new String[]{"Salvo com sucesso!"};
            PlayJogo.getJogadores().add(new JogadoresController().getJogador(result));
        }
        return new JSONArray().put(new OperacoesJSON().toJSONObject(key, value));
    }

    private JSONArray login(String email, String senha) {
        String[] value, key = {"result"};
        int result = 0;
        for (int i = 0; i < PlayJogo.getJogadores().size(); i++) {
            if (PlayJogo.getJogadores().get(i).getLogin().equals(email)
                    && PlayJogo.getJogadores().get(i).getSenha().equals(senha)) {
                result = 1;
            }
            System.err.println(PlayJogo.getJogadores().get(i).getSenha()+" email: "+PlayJogo.getJogadores().get(i).getLogin());
        }
        value = new String[]{String.valueOf(result)};
        return new JSONArray().put(new OperacoesJSON().toJSONObject(key, value));
    }

    private JSONArray newJogo(int jogo_id, int jogador_id, String nome) throws JSONException {
        PlayJogo.getJogos().add(new JogosController().getJogo(jogo_id));
        JSONArray json = new JSONArray();
        json.put(0,PlayJogo.getJogos());
        json.put(1,PlayJogo.getMecTextos());
        json.put(2,PlayJogo.getMecFotos());
        return json;
    }

    private JSONArray listarJogos(double latitude, double longitude, int distancia) {
        return new JogosController().getJogos(latitude, longitude, distancia);
    }

}
