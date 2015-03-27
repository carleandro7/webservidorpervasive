/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.resource;

import br.com.great.controller.JogadoresController;
import br.com.great.helpful.Constants;
import br.com.great.helpful.OperacoesJSON;
import br.com.great.gerenciamento.ServidorJogo;
import br.com.great.model.Jogador;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.json.JSONArray;

/**
 * REST Web Service jogadro
 *
 * @author carleandro
 */
@Path("jogador")
public class JogadorResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ImageResource
     */
    public JogadorResource() {
    }

    /**
     *
     * Método responsável por fazer chamada ao controller listando todos os
     * jogadores
     *
     * @return ArrayList tipo Jogador
     * @author Carleandro Noleto
     * @since 27/11/2014
     * @version 1.0
     */
    @GET
    @Path("/listarTodos")
    @Produces("application/json")
    public ArrayList<Jogador> listarTodos() {
        return new JogadoresController().listarTodos();
    }

    /**
     * Método responsável por get em todos os dados do jogador
     * @param id String
     * @return Jogador
     */
    @GET
    @Path("/getJogador")
    @Produces("application/json")
    public Jogador getJogador(@QueryParam("id") String id) {
        return new JogadoresController().getJogador(Integer.valueOf(id));
    }

    /**
     * Método responsável por realizar o login do jogador no servidor
     *
     * @param email String
     * @param password String
     * @return String
     * @author Carleandro Noleto
     * @since 27/11/2014
     * @version 1.0
     */
    @GET
    @Path("login")
    @Produces("application/json")
    public Jogador login(@QueryParam("email") String email, @QueryParam("password") String password) {
        return new JogadoresController().login(email, password);
    }

    /**
     * Metodo responsavel por cadastrar jogador
     * @param email String
     * @param password String
     * @return String
     */
    @GET
    @Path("cadastrarJogador")
    @Produces("application/json")
    public String cadastrarJogador(@QueryParam("email") String email, @QueryParam("password") String password) {
        String[][] key = {{"email","password"}};
        String[][] value = {{email, password}};
       return ServidorJogo.getInstance().acao(Constants.JOGADOR_CADJOGADOR, 0,  (new OperacoesJSON().toJSONArray(key, value))).toString();
    }

    /**
     * Funcao de registrar o dispositivo do jogador
     * @param jogador_id String
     * @param device_id String
     * @return String
     */
    @GET
    @Path("registroDevice")
    @Produces("application/json")
    public String registroDevice(@QueryParam("jogador_id") String jogador_id, @QueryParam("device_id") String device_id) {
        String[][] key = {{"jogador_id","device_id"}};
        String[][] value = {{jogador_id, device_id}};
        JSONArray json = ServidorJogo.getInstance().acao(Constants.JOGADOR_REGDISPOSITIVO, 0,  (new OperacoesJSON().toJSONArray(key, value)));
        return new OperacoesJSON().toJSONObject(json, 0, "result");
    }
    
    
    /**
     * PUT method for updating or creating an instance of JogoResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

}
