/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.great.resource;

import br.com.great.contexto.CapturarObjeto;
import br.com.great.contexto.ConfiguracaoMissao;
import br.com.great.contexto.Jogo;
import br.com.great.contexto.Mecanica;
import br.com.great.contexto.Missao;
import br.com.great.contexto.Texto;
import br.com.great.gerenciamento.GerenciadorJogos;
import br.com.great.gerenciamento.PlayJogo;
import java.util.ArrayList;
import java.util.Collection;
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
        Texto t = new Texto();
        t.setMecsimples_id(1);
        t.setTexto("aaaaaaaa");
        t.setCapturarObjeto(new CapturarObjeto());
        t.getCapturarObjeto().setTipo(99);
        t.getCapturarObjeto().setNome("carleandro");
        PlayJogo.getMecTextos().add(t);
        
        Missao m = new Missao();
        m.setMecanica(new ArrayList<Mecanica>());
        m.getMecanica().add(t.getCapturarObjeto());
                
        PlayJogo.getMissoes().add(m);
        
        System.err.println(PlayJogo.getMissoes().get(0).getMecanica().get(0).getNome());
        System.err.println(PlayJogo.getMecTextos().get(0).getCapturarObjeto().getNome());
        
        PlayJogo.getMecTextos().get(0).getCapturarObjeto().setNome("olivia");
        System.err.println(PlayJogo.getMissoes().get(0).getMecanica().get(0).getNome());
        System.err.println(PlayJogo.getMecTextos().get(0).getCapturarObjeto().getNome());
    }
    
}
