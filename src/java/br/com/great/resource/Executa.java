/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.great.resource;

import br.com.great.management.EstadoJogo;
import br.com.great.management.GerenciamentoJogos;
import org.json.JSONException;


/**
 *
 * @author carleandro
 */
public class Executa {
    /**
     *
     * @param args String
     */
    public static void main(String[] args)  { 
        
        GerenciamentoJogos jogo = new GerenciamentoJogos();
        jogo.carregaJogos();
    }
    
}