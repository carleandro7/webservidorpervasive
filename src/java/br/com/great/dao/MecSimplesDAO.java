/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.great.dao;

import br.com.great.factory.ConnectionFactory;
import br.com.great.model.MecSimples;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe responsavel realizar toda a interação com banco de dados relacionado com entidade mecanica
 * @author carleandro
 */
public class MecSimplesDAO extends ConnectionFactory{
    
    private static MecSimplesDAO instance;
                /**
	 * 
	 * Método responsável por criar uma instancia da classe MecSimplesDAO (Singleton)
	 *
	 * @return static
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
	public static MecSimplesDAO getInstance(){
		if(instance == null)
			instance = new MecSimplesDAO();
		return instance;
	}
        
        /**
	 * 
	 * Método responsável por listar todos as Mecanicas das missoes de um jogo do banco
	 *
         * @param jogo_id String
	 * @return JSONArray
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
	public JSONArray getTodos(String jogo_id){
                PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray mecanicas = null;
		Connection conexao = criarConexao();
		try {
                        mecanicas= new JSONArray();
                         String sql = "SELECT `mecanicas`.`id`, `mecanicas`.`nome`, `mecanicas`.`tipo`, `mecanicas`.`tempo`, "
                                 + "`mecanicas`.`missoes_id`, `mecanicas`.`latitude`, `mecanicas`.`longitude` FROM `mecanicas` " +
                                       " LEFT JOIN `missoes` ON (`mecanicas`.`missoes_id` = `missoes`.`id`) " +
                                       " LEFT JOIN `grupos`  ON (`missoes`.`grupo_id` = `grupos`.`id`) " +
                                       " WHERE  `grupos`. `jogo_id` = "+jogo_id;
                
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
                
			while(rs.next()){
				JSONObject mecanica = new JSONObject();
                                mecanica.put("mecsimples_id",rs.getInt("id"));
                                mecanica.put("nome",rs.getString("nome"));
                                mecanica.put("tipoMecanica",rs.getString("tipo"));
                                mecanica.put("tempo",rs.getTime("tempo"));
                                mecanica.put("latitude",rs.getDouble("latitude"));
                                mecanica.put("longitude",rs.getDouble("longitude"));
                                mecanica.put("missoes_id",rs.getInt("missoes_id"));
                                
                                
                            switch (rs.getString("tipo")) {
                                case "vtextos":
                                    mecanica.put("mecanica", new TextosDAO().getMecVTexto(rs.getInt("id")));
                                    break;
                                case "vfotos":
                                    mecanica.put("mecanica", new FotosDAO().getMecVFotos(rs.getInt("id")));
                                    break;
                                case "irlocais":
                                    mecanica.put("mecanica", new IrLocaisDAO().getMecIrLocais(rs.getInt("id")));
                                    break;
                                case "cfotos":
                                    mecanica.put("mecanica", new FotosDAO().getMecCFotos(rs.getInt("id")));
                                    break;
                                case "csons":
                                    mecanica.put("mecanica", new SonsDAO().getMecCSons(rs.getInt("id")));
                                    break;    
                                case "cvideos":
                                    mecanica.put("mecanica", new VideosDAO().getMecCVideos(rs.getInt("id")));
                                    break;    
                                case "ctextos":
                                    mecanica.put("mecanica", new TextosDAO().getMecCTextos(rs.getInt("id")));
                                    break;    
                            }
                                mecanicas.put(mecanica);
			}
			
		} catch (SQLException | JSONException e) {
			System.out.println("Erro ao listar todas as mecanicas: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return mecanicas;
	}
        
        /**
	 * 
	 * Método responsável por get em uma MecSimples de uma missao de um jogo do banco de dados
	 *
         * @param mecanica_id String
	 * @return JSONArray
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
        public JSONArray getMecania(String mecanica_id){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray mecanicas = null;
		Connection conexao = criarConexao();
		try {
                        mecanicas= new JSONArray();
                         String sql = "SELECT * FROM mecanicas " +
                                       " WHERE  id = "+mecanica_id+"  ORDER BY ordem ";
                
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
                
			while(rs.next()){
				JSONObject mecanica = new JSONObject();
                                mecanica.put("mecanica_id",rs.getInt("id"));
                                mecanica.put("nome",rs.getString("nome"));
                                mecanica.put("tipo",rs.getString("tipo"));
                                mecanica.put("ordem",rs.getInt("ordem"));
                                mecanica.put("tempo",rs.getTime("tempo"));
                                mecanica.put("latitude",rs.getDouble("latitude"));
                                mecanica.put("longitude",rs.getDouble("longitude"));
                                mecanica.put("missoes_id",rs.getInt("missoes_id"));            
                            switch (rs.getString("tipo")) {
                                case "vtextos":
                                    mecanica.put("mecanica", new TextosDAO().getMecVTexto(rs.getInt("id")));
                                    break;
                                case "vfotos":
                                    mecanica.put("mecanica", new FotosDAO().getMecVFotos(rs.getInt("id")));
                                    break;
                                case "irlocais":
                                    mecanica.put("mecanica", new IrLocaisDAO().getMecIrLocais(rs.getInt("id")));
                                    break;
                                case "cfotos":
                                    mecanica.put("mecanica", new FotosDAO().getMecCFotos(rs.getInt("id")));
                                    break;
                                case "csons":
                                    mecanica.put("mecanica", new SonsDAO().getMecCSons(rs.getInt("id")));
                                    break; 
                                case "cvideos":
                                    mecanica.put("mecanica", new VideosDAO().getMecCVideos(rs.getInt("id")));
                                    break;    
                                case "ctextos":
                                    mecanica.put("mecanica", new TextosDAO().getMecCTextos(rs.getInt("id")));
                                    break;    
                            }
                                mecanicas.put(mecanica);
			}
                        
			
		} catch (SQLException | JSONException e) {
			System.out.println("Erro ao listar dados de uma mecanica: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return mecanicas;
	}
        
        /**
	 * 
	 * Método responsável por lista todas as mecanicas de uma missao
	 *
         * @param missao_id int
	 * @return JSONArray
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
        public ArrayList<MecSimples> getMecaniaMissao(int missao_id){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MecSimples> mecanicas = null;
		Connection conexao = criarConexao();
		try {
                        mecanicas= new ArrayList<MecSimples>();
                         String sql = "SELECT * FROM mecsimples "
                                 + " LEFT JOIN mecanica ON(mecanica.id = mecsimples.mecanica_id) "
                                 + " WHERE mecanica.missoes_id = "+missao_id+" AND mecanica.tipo=0"; 
                
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
                
			while(rs.next()){
				MecSimples mecanica = new MecSimples();
                                mecanica.setMecsimples_id(rs.getInt("mecsimples.id"));
                                mecanica.setNome(rs.getString("mecanica.nome"));
                                mecanica.setTipoMecanica(rs.getString("mecsimples.tipo"));
                                mecanica.setTipo(rs.getInt("mecanica.tipo"));
                                mecanica.setTempo(rs.getTime("mecsimples.tempo"));
                                mecanica.setLatitude(rs.getDouble("mecsimples.latitude"));
                                mecanica.setLongitude(rs.getDouble("mecsimples.longitude"));
                                mecanica.setVisivel(rs.getInt("mecsimples.visivel"));
                                mecanica.setMecanica_id(rs.getInt("mecsimples.mecanica_id"));
                                mecanica.setMissoes_id(rs.getInt("mecanica.missoes_id"));
                                mecanica.setMecanica_id(rs.getInt("mecanica.id"));
                                JSONObject object = new JSONObject();
                                switch (rs.getString("mecsimples.tipo")) {
                                case "vtextos":
                                    object.put("mecanica", new TextosDAO().getMecVTexto(rs.getInt("mecsimples.id")));
                                    mecanica.setMecanica(object);
                                    break;
                                case "vfotos":
                                    object.put("mecanica", new FotosDAO().getMecVFotos(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;
                                case "irlocais":
                                    object.put("mecanica", new IrLocaisDAO().getMecIrLocais(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;
                                case "cfotos":
                                    object.put("mecanica", new FotosDAO().getMecCFotos(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;
                                case "csons":
                                    object.put("mecanica", new SonsDAO().getMecCSons(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break; 
                                case "cvideos":
                                    object.put("mecanica", new VideosDAO().getMecCVideos(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;    
                                case "ctextos":
                                    object.put("mecanica", new TextosDAO().getMecCTextos(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;    
                            }
                                mecanicas.add(mecanica);
			}
			
                        	
		} catch (SQLException | JSONException e) {
			System.out.println("Erro ao listar dados de uma mecanica: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return mecanicas;
	}
        /**
	 * 
	 * Método responsável retornar a mecanica de uma missao
	 *
         * @param missao_id int
	 * @return JSONArray
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
        public MecSimples getMecaniaMissao(int mecanica_id, int missao_id){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conexao = criarConexao();
		try {
                         String sql = "SELECT * FROM mecsimples "
                                 + " LEFT JOIN mecanica ON(mecanica.id = mecsimples.mecanica_id) "
                                 + " WHERE mecanica.missoes_id = "+missao_id+" AND mecanica.id="+mecanica_id; 
                
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
                
			while(rs.next()){
				MecSimples mecanica = new MecSimples();
                                mecanica.setMecsimples_id(rs.getInt("mecsimples.id"));
                                mecanica.setNome(rs.getString("mecanica.nome"));
                                mecanica.setTipoMecanica(rs.getString("mecsimples.tipo"));
                                mecanica.setTipo(rs.getInt("mecanica.tipo"));
                                mecanica.setTempo(rs.getTime("mecsimples.tempo"));
                                mecanica.setLatitude(rs.getDouble("mecsimples.latitude"));
                                mecanica.setLongitude(rs.getDouble("mecsimples.longitude"));
                                mecanica.setVisivel(rs.getInt("mecsimples.visivel"));
                                mecanica.setMecanica_id(rs.getInt("mecsimples.mecanica_id"));
                                mecanica.setMissoes_id(rs.getInt("mecanica.missoes_id"));
                                mecanica.setMecanica_id(rs.getInt("mecanica.id"));
                                mecanica.setRequisitos(listRequisitos(mecanica.getMecanica_id()));
                                JSONObject object = new JSONObject();
                                switch (rs.getString("mecsimples.tipo")) {
                                case "vtextos":
                                    object.put("mecanica", new TextosDAO().getMecVTexto(rs.getInt("mecsimples.id")));
                                    mecanica.setMecanica(object);
                                    break;
                                case "vfotos":
                                    object.put("mecanica", new FotosDAO().getMecVFotos(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;
                                case "irlocais":
                                    object.put("mecanica", new IrLocaisDAO().getMecIrLocais(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;
                                case "cfotos":
                                    object.put("mecanica", new FotosDAO().getMecCFotos(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;
                                case "csons":
                                    object.put("mecanica", new SonsDAO().getMecCSons(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break; 
                                case "cvideos":
                                    object.put("mecanica", new VideosDAO().getMecCVideos(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;    
                                case "ctextos":
                                    object.put("mecanica", new TextosDAO().getMecCTextos(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;    
                            }
                               return mecanica;
			}
			
                        	
		} catch (SQLException | JSONException e) {
			System.out.println("Erro ao listar dados de uma mecanica: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return null;
	}
        public ArrayList<MecSimples> getMecaniaMecComposta(int mecComposta_id){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MecSimples> mecanicas = null;
		Connection conexao = criarConexao();
		try {
                        mecanicas= new ArrayList<MecSimples>();
                         String sql = "SELECT * FROM composta LEFT JOIN mecsimples ON(mecsimples.id=composta.mecsimples_id) "
                                 + " LEFT JOIN mecanica ON(mecanica.id = mecsimples.mecanica_id) "
                                 + " WHERE composta.meccomposta_id ="+mecComposta_id;
                
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
                
			while(rs.next()){
				MecSimples mecanica = new MecSimples();
                                mecanica.setMecsimples_id(rs.getInt("mecsimples.id"));
                                mecanica.setNome(rs.getString("mecanica.nome"));
                                mecanica.setTipoMecanica(rs.getString("mecsimples.tipo"));
                                mecanica.setTipo(rs.getInt("mecanica.tipo"));
                                mecanica.setTempo(rs.getTime("mecsimples.tempo"));
                                mecanica.setLatitude(rs.getDouble("mecsimples.latitude"));
                                mecanica.setLongitude(rs.getDouble("mecsimples.longitude"));
                                mecanica.setVisivel(rs.getInt("mecsimples.visivel"));
                                mecanica.setMecanica_id(rs.getInt("mecsimples.mecanica_id"));
                                mecanica.setMissoes_id(rs.getInt("mecanica.missoes_id"));
                                mecanica.setMecanica_id(rs.getInt("mecanica.id"));
                                mecanica.setRequisitos(listRequisitos(mecanica.getMecanica_id()));
                                JSONObject object = new JSONObject();
                                switch (rs.getString("mecsimples.tipo")) {
                                case "vtextos":
                                    object.put("mecanica", new TextosDAO().getMecVTexto(rs.getInt("mecsimples.id")));
                                    mecanica.setMecanica(object);
                                    break;
                                case "vfotos":
                                    object.put("mecanica", new FotosDAO().getMecVFotos(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;
                                case "irlocais":
                                    object.put("mecanica", new IrLocaisDAO().getMecIrLocais(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;
                                case "cfotos":
                                    object.put("mecanica", new FotosDAO().getMecCFotos(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;
                                case "csons":
                                    object.put("mecanica", new SonsDAO().getMecCSons(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break; 
                                case "cvideos":
                                    object.put("mecanica", new VideosDAO().getMecCVideos(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;    
                                case "ctextos":
                                    object.put("mecanica", new TextosDAO().getMecCTextos(rs.getInt("id")));
                                    mecanica.setMecanica(object);
                                    break;    
                            }
                                mecanicas.add(mecanica);
			}
			
                        	
		} catch (SQLException | JSONException e) {
			System.out.println("Erro ao listar dados de uma mecanica: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return mecanicas;
	}
        
        public ArrayList<Integer> listRequisitos(int mecanica_id){
            	PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Integer> requisitos = null;
		Connection conexao = criarConexao();
		try {
                        requisitos= new ArrayList<Integer>();
                         String sql = "SELECT * FROM reqmecanica "
                                 + " WHERE mecanica_id ="+mecanica_id;
                
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
                
			while(rs.next()){
                                requisitos.add(rs.getInt("req_id"));
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao listar requisitos de mecanica: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return requisitos;
        }
        
}
