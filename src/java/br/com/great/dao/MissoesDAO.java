/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.great.dao;

import br.com.great.factory.ConnectionFactory;
import br.com.great.model.Mecanica;
import br.com.great.model.Missao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe responsavel realizar toda a interação com banco de dados relacionado com entidade missoes
 * @author carleandro
 */
public class MissoesDAO extends ConnectionFactory{
    
    private static MissoesDAO instance;
                /**
	 * 
	 * Método responsável por criar uma instancia da classe MissoesDAO (Singleton)
	 *
	 * @return static
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
	public static MissoesDAO getInstance(){
		if(instance == null)
			instance = new MissoesDAO();
		return instance;
	}
        
        	/**
	 * 
	 * Método responsável por listar todos os missoes dos grupos de um jogo do banco
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
		JSONArray missoes = null;
		Connection conexao = criarConexao();
		try {
                        missoes= new JSONArray();
                         String sql = "SELECT  `missoes`.`id`, `missoes`.`nome`, `missoes`.`ordem`, `missoes`.`grupo_id`   FROM `grupos` " +
                                " LEFT JOIN `missoes`  ON (`missoes`.`grupo_id` = `grupos`.`id`) " +
                                " WHERE  `grupos`. `jogo_id` = "+jogo_id;
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				JSONObject missao = new JSONObject();
                                missao.put("id",rs.getInt("id"));
                                missao.put("nome",rs.getString("nome"));
                                missao.put("ordem",rs.getInt("ordem"));
                                missao.put("grupo_id",rs.getInt("grupo_id"));
                                
				missoes.put(missao);
			}
                        
			
		} catch (SQLException | JSONException e) {
			System.out.println("Erro ao listar todas as missoes: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return missoes;
	}
        
         /**
	 * Método responsável por listar todos as missoes de um grupo
	 *
         * @param grupo_id String
	 * @return JSONArray
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
	public ArrayList<Missao> getMissoesGrupo(int grupo_id){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Missao> missoes = null;
		Connection conexao = criarConexao();
		try {
                        missoes= new ArrayList<Missao>();
                         String sql = "SELECT  *  FROM missoes  WHERE  grupo_id = "+grupo_id;
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Missao missao = new Missao();
                                missao.setId(rs.getInt("id"));
                                missao.setNome(rs.getString("nome"));
                                missao.setOrdem(rs.getInt("ordem"));
                                missao.setGrupo_id(rs.getInt("grupo_id"));
                                missao.setLongitude(rs.getDouble("longitude"));
                                missao.setLatitude(rs.getDouble("latitude"));
                                missao.setRequisitos(listRequisitos(missao.getId()));
				missoes.add(missao);
			}
                        
			
		} catch (SQLException e) {
			System.out.println("Erro ao listar todas as missoes: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return missoes;
	}

	/**
	 * 
	 * Método responsável por listar todas as missoes em uma distancia X para um jogador
	 *
         * @param grupo_id Id grupo das missoes
         * @param distancia Distancia do raio para pesquisar
         * @param latitude String
         * @param longitude String
         * @param missoes ArrayObject
         * @param sqlMissoes Id das missoes que nao devem esta na lista
	 * @return JSONArray Retorna id das missoes
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
	public JSONArray getMissoesRegiao(int distancia, int grupo_id, String latitude, String longitude, JSONArray missoes, String sqlMissoes){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
                String sql ="";
		Connection conexao = criarConexao();
		try {
                        if(distancia != 0){
                         sql = "select missoes.id from missoes where missoes.grupo_id ="+
                                 grupo_id+" AND (((3956 * 2 * ASIN(SQRT(POWER(SIN((abs("+latitude+")"
                                 + " - abs(missoes.latitude)) * pi()/180 / 2),2) + COS(abs("+latitude+") "
                                 + "* pi()/180 ) * COS(abs(missoes.latitude) * pi()/180) * POWER(SIN((abs("+longitude+") "
                                 + "- abs(missoes.longitude)) * pi()/180 / 2), 2)))) * 1.609344) < "+distancia+")"+sqlMissoes;
                        }else{ 
                            sql = "select missoes.id from missoes where missoes.grupo_id ="+
                                 grupo_id+" "+sqlMissoes;
                        }
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				JSONObject missao = new JSONObject();
                                missao.put("id",rs.getInt("missoes.id"));
                                missao.put("prioridade",distancia);
				missoes.put(missao);
			}
		} catch (SQLException | JSONException e) {
			System.out.println("Erro ao listar todas as missoes em uma distancia: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return missoes;
	}
        
         /**
	 * Método responsável por listar todas as mecanicas de missao
	 *
         * @param missao_id String
	 * @return JSONArray
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
	public ArrayList<Mecanica> getMissaoMecanicas(int missao_id){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Mecanica> mecanicas = null;
		Connection conexao = criarConexao();
		try {
                        mecanicas= new ArrayList<Mecanica>();
                         String sql = "SELECT  *  FROM mecanica  WHERE  missoes_id = "+missao_id;
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Mecanica mecanica = new Mecanica();
                                mecanica.setMecanica_id(rs.getInt("id"));
                                mecanica.setNome(rs.getString("nome"));
                                mecanica.setTipo(rs.getInt("tipo"));
                                mecanica.setMissoes_id(rs.getInt("missoes_id"));
				mecanicas.add(mecanica);
			}
                        
			
		} catch (SQLException e) {
			System.out.println("Erro ao listar todas as missoes: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return mecanicas;
	}
        
        public ArrayList<Integer> listRequisitos(int missao_id){
            	PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Integer> requisitos = null;
		Connection conexao = criarConexao();
		try {
                        requisitos= new ArrayList<Integer>();
                         String sql = "SELECT * FROM reqmissao "
                                 + " WHERE missoes_id ="+missao_id;
                
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
