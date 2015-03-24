/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.controller;

import br.com.great.dao.MecCompostaDAO;
import br.com.great.dao.MecSimplesDAO;
import br.com.great.model.MecComposta;
import br.com.great.model.MecSimples;
import java.util.ArrayList;
import org.json.JSONArray;

/**
 * Classe responsavel por fazer o controle entre os serviços oferecidos pelo jogos
 * no banco de dados com os eventos relacionados a cada jogo
 * @author carleandro
 */
public class MecanicaController {
    
    /**
    * Método responsável por get em uma MecSimples de uma missao de um jogo do banco de dados
     * @param mecanica_id String
     * @return JSONArray
     */
    public JSONArray getMecania(String mecanica_id){
        System.out.println("Enviando para o GIT");
        return MecSimplesDAO.getInstance().getMecania(mecanica_id);
    }
    
    /**
    * Método responsável lista todas as mecanicas de uma missao
     * @param missao_id int
     * @return ArrayList MecSimples
     */
    public ArrayList<MecSimples> getMecaniaMissao(int missao_id){
        System.out.println("Enviando para o GIT");
        return MecSimplesDAO.getInstance().getMecaniaMissao(missao_id);
    }
    /**
    * Método responsável retorna a mecanica de uma missao
     * @param mecanica_id
     * @param missao_id int
     * @return ArrayList MecSimples
     */
    public MecSimples getMecSimplesMissao(int mecanica_id, int missao_id){
        System.out.println("Enviando para o GIT");
        return MecSimplesDAO.getInstance().getMecaniaMissao(mecanica_id,missao_id);
    }
    /**
    * Método responsável retorna a mecanica de uma missao
     * @param mecanica_id
     * @param missao_id int
     * @return ArrayList MecSimples
     */
    public MecComposta getMecCompostaMissao(int mecanica_id, int missao_id){
        System.out.println("Enviando para o GIT");
        return MecCompostaDAO.getInstance().getMecaniaMissao(mecanica_id,missao_id);
    }
}
