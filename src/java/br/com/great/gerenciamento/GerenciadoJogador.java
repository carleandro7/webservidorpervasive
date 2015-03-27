package br.com.great.gerenciamento;

import br.com.great.controller.JogadoresController;
import br.com.great.dao.JogadoresDAO;
import br.com.great.gerenciamento.PlayJogo;
import br.com.great.helpful.OperacoesJSON;
import br.com.great.model.Jogador;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author carleandro
 */
public class GerenciadoJogador {

    public JSONArray setJogadorLocalizacao(JSONArray json) {
        int jogador_id = Integer.valueOf(new OperacoesJSON().toJSONObject(json, 0, "jogador_id"));
        boolean result = false;
        Map<String, String> params = new HashMap<String, String>();
        for (int i = 0; i < PlayJogo.getListJogadores().size(); i++) {
            if (PlayJogo.getListJogadores().get(i).getJogador().getId() == jogador_id) {
                PlayJogo.getListJogadores().get(i).getJogador().setLatitude(Double.valueOf(new OperacoesJSON().toJSONObject(json, 0, "latitude")));
                PlayJogo.getListJogadores().get(i).getJogador().setLongitude(Double.valueOf(new OperacoesJSON().toJSONObject(json, 0, "longitude")));
                PlayJogo.getListJogadores().get(i).setAtualizarLocalizacao(1);
                result = true;
                break;
            }
        }
        String[][] key = {{"result"}};
        String[][] value = {{String.valueOf(result)}};
        return new OperacoesJSON().toJSONArray(key, value);
    }

    public JSONArray cadastrarJogador(String email, String password) {
        Jogador jogador = new JogadoresController().cadastrarJogador(email, password);
        boolean salvo;
        String mensagem = "";
        if (jogador != null) {
            salvo = true;
            mensagem = "Cadastrado com sucesso!";
        } else {
            salvo = false;
        }
        JSONArray json = new JSONArray();
        json.put("{\"salvo\":\"" + salvo + "\",\"mensagem\":\"" + mensagem + "\"}");
        return json;
    }

    public JSONArray registrarDevice(int jogador_id, String device_id) {
        boolean result = false;
        for (int i = 0; i < PlayJogo.getListJogadores().size(); i++) {
            if (PlayJogo.getListJogadores().get(i).getJogador().getId() == jogador_id) {
                result = JogadoresDAO.getInstance().registroDevice(jogador_id, device_id);
                PlayJogo.getListJogadores().get(i).getJogador().setIddispositivo(device_id);
                break;
            }
        }
        return new OperacoesJSON().toJSONArray("result", String.valueOf(result));
    }

}
