/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.resource;

import br.com.great.GCMGoogle.EnviarMensagemGCM;
import br.com.great.management.ServidorJogo;
import br.com.great.helpful.Constants;
import br.com.great.helpful.OperacoesJSON;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.json.JSONArray;

/**
 * REST Web Service Grupo
 *
 * @author carleandro
 */
@Path("grupo")
public class GrupoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GruposResource
     */
    public GrupoResource() {
    }

    /**
     * Método responsável get todos os grupos de um jogo
     *
     * @param jogo_id String
     * @return String
     * @author Carleandro Noleto
     * @since 10/12/2014
     * @version 1.0
     */
    @GET
    @Path("/getGrupos")
    @Produces("application/json")
    public String getGrupos(@QueryParam("jogo_id") String jogo_id) {
        return ServidorJogo.getInstance().acao(Constants.JOGO_LISTAGRUPOS, Integer.valueOf(jogo_id), null).toString();
    }

    /**
     * Método responsável por seta um jogador em um grupo
     *
     * @param jogo_id String
     * @param grupo_id String
     * @param jogador_id String
     * @return String
     * @author Carleandro Noleto
     * @since 10/12/2014
     * @version 1.0
     */
    @GET
    @Path("/setGrupoParticipando")
    @Produces("application/json")
    public String setGrupoParticipando(@QueryParam("jogo_id") String jogo_id, @QueryParam("grupo_id") String grupo_id,
            @QueryParam("jogador_id") String jogador_id) {
        String[][] key = {{"jogador_id"}};
        String[][] value = {{jogador_id}};
        return ServidorJogo.getInstance().acaoGrupo(Constants.GRUPO_INSERIRPARTICIPANTE, Integer.valueOf(grupo_id), Integer.valueOf(jogo_id), (new OperacoesJSON().toJSONArray(key, value))).toString();
    }

    /**
     * Método responsável lista todos os arquivos para um jogador a depender da
     * localização
     *
     * @param jogo_id String
     * @param grupo_id String
     * @param latitude String
     * @param longitude String
     * @return String
     * @author Carleandro Noleto
     * @since 10/12/2014
     * @version 1.0
     */
    @GET
    @Path("/getTodosArquivos")
    @Produces("application/json")
    public String getTodosArquivos(@QueryParam("jogo_id") String jogo_id, @QueryParam("grupo_id") String grupo_id, @QueryParam("latitude") String latitude,
            @QueryParam("longitude") String longitude) {
        String[][] key = {{"latitude", "longitude"}};
        String[][] value = {{latitude, longitude}};
        return ServidorJogo.getInstance().acaoGrupo(Constants.GRUPO_LISTAARQUIVOS, Integer.valueOf(grupo_id), Integer.valueOf(jogo_id), (new OperacoesJSON().toJSONArray(key, value))).toString();
    }

    @GET
    @Path("/getMecanicaAtual")
    @Produces("application/json")
    public String getMecanicaAtual(@QueryParam("jogo_id") String jogo_id, @QueryParam("grupo_id") String grupo_id) {
        return ServidorJogo.getInstance().acaoGrupo(Constants.GRUPO_MECANICAATUAL, Integer.valueOf(grupo_id), Integer.valueOf(jogo_id), new JSONArray()).toString();
    }

    @GET
    @Path("/getMecanicaLiberada")
    @Produces("application/json")
    public String getMecanicaLiberada(@QueryParam("jogo_id") String jogo_id, @QueryParam("grupo_id") String grupo_id, @QueryParam("mecanica_id") String mecanica_id) {
        String[][] key = {{"mecanica_id"}};
        String[][] value = {{mecanica_id}};
        return ServidorJogo.getInstance().acaoGrupo(Constants.GRUPO_GETMECLIBERADA, Integer.valueOf(grupo_id), Integer.valueOf(jogo_id), (new OperacoesJSON().toJSONArray(key, value))).toString();
    }

    @GET
    @Path("/setStatusMecanica")
    @Produces("application/json")
    public String setStatusMecanica(@QueryParam("jogo_id") String jogo_id, @QueryParam("grupo_id") String grupo_id,
            @QueryParam("mecanica_id") String mecanica_id, @QueryParam("jogador_id") String jogador_id) {
        String[][] key = {{"mecanica_id", "jogador_id"}};
        String[][] value = {{mecanica_id, jogador_id}};
        return ServidorJogo.getInstance().acaoGrupo(Constants.GRUPO_SETSTATUSMECANICA, Integer.valueOf(grupo_id), Integer.valueOf(jogo_id), (new OperacoesJSON().toJSONArray(key, value))).toString();
    }

    @GET
    @Path("/setLocalizacaoJogador")
    @Produces("application/json")
    public String setLocalizacaoJogador(@QueryParam("jogo_id") String jogo_id, @QueryParam("grupo_id") String grupo_id,
            @QueryParam("jogador_id") String jogador_id, @QueryParam("latitude") String latitude, @QueryParam("longitude") String longitude) {
        String[][] key = {{"jogador_id", "latitude", "longitude"}};
        String[][] value = {{jogador_id, latitude, longitude}};
        return ServidorJogo.getInstance().acaoGrupo(Constants.JOGADOR_SETLOCALIZACAO, Integer.valueOf(grupo_id), Integer.valueOf(jogo_id), (new OperacoesJSON().toJSONArray(key, value))).toString();
    }

    @GET
    @Path("/setMensagem")
    @Produces("application/json")
    public String setMensagem(@QueryParam("regs_id") String regs_id, @QueryParam("user") String user,
            @QueryParam("tipoacao") String tipoacao) {
        List<String> regIdList = new ArrayList<String>();
        regIdList.add(regs_id);
        Map<String, String> params = new HashMap<String, String>();
        params.put("user", user);
        params.put("tipoacao", tipoacao);
        new EnviarMensagemGCM().enviarParaDeviceBckMap(params, regIdList);
        return "true";
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
