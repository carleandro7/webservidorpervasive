/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.great.dao;

import br.com.great.factory.ConnectionFactory;
import br.com.great.model.Grupo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe responsavel realizar toda a interação com banco de dados relacionado com entidade Grupo
 * @author carleandro
 */
public class GruposDAO extends ConnectionFactory{
    
    private static GruposDAO instance;
         /**
	 * 
	 * Método responsável por criar uma instancia da classe GruposDAO (Singleton)
	 *
	 * @return static
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
	public static GruposDAO getInstance(){
		if(instance == null)
			instance = new GruposDAO();
		return instance;
	}
        
        /**
	 * 
	 * Método responsável por listar todos os grupos de um jogo do banco
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
		JSONArray grupos = null;
		Connection conexao = criarConexao();
		try {
                        grupos= new JSONArray();
                        pstmt = conexao.prepareStatement("select * from grupos where jogo_id = "+jogo_id+" order by nome");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				JSONObject grupo = new JSONObject();
                                grupo.put("id",rs.getInt("id"));
                                grupo.put("nome",rs.getString("nome"));
                                grupo.put("jogo_id",rs.getString("jogo_id"));
				grupos.put(grupo);
			}
			
		} catch (SQLException | JSONException e) {
			System.out.println("Erro ao listar todos os grupos: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return grupos;
	}
        
        /**
	 * 
	 * Método responsável por listar todos os grupos de um jogo do banco
	 *
         * @param jogo_id String
	 * @return ArrayList grupo
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
	public ArrayList<Grupo> getTodosGrupos(String jogo_id){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Grupo> grupos = null;
		Connection conexao = criarConexao();
		try {
                        grupos= new ArrayList<Grupo>();
                        pstmt = conexao.prepareStatement("select * from grupos where jogo_id = "+jogo_id+" order by nome");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				Grupo grupo = new Grupo();
                                grupo.setId(rs.getInt("id"));
                                grupo.setNome(rs.getString("nome"));
                                grupo.setJogo_id(rs.getInt("jogo_id"));
				grupos.add(grupo);
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao listar todos os grupos: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return grupos;
	}
    /**
	 * 
	 * Método responsável por verificar se jogador esta participando de um grupo
	 *
         * @param jogo_id id do jogo 
         * @param grupo_id id do grupo 
         * @param jogador_id id do jogador
	 * @return boolean true se o jogador esta participando do jogo no grupo expeciaficado
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
    public boolean getGrupoParticipando(int jogo_id, int grupo_id, int jogador_id) {
        	PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Grupo> grupos = null;
		Connection conexao = criarConexao();
		try {
                        pstmt = conexao.prepareStatement("select * from jogador_jogos where jogo_id = "+jogo_id+" AND jogador_id="+jogador_id );
			rs = pstmt.executeQuery();
			if(rs.next()){
			  return true;	
			}
		} catch (SQLException e) {
			System.out.println("Erro ao verificar participante em grupo: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return false;
    }
    /**
	 * 
	 * Método responsável por adicionar jogador a um grupo
	 *
         * @param jogo_id id do jogo 
         * @param grupo_id id do grupo 
         * @param jogador_id id do jogador
	 * @return int 1 se inseriou o jogador com sucesso
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
    public int setGrupoParticipando(int jogo_id, int grupo_id, int jogador_id) {
        	PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Grupo> grupos = null;
		Connection conexao = criarConexao();
		try {
                        pstmt = conexao.prepareStatement("INSERT INTO  jogador_jogos(`jogador_id`,`jogo_id` , `grupo_id`) VALUES (?, ?, ?)");
                            pstmt.setInt(1, jogador_id);
                            pstmt.setInt(2, jogo_id);
                            pstmt.setInt(3, grupo_id);
                            pstmt.executeUpdate();
                            return 1;
		} catch (SQLException e) {
			System.out.println("Erro ao salvar participante em grupo: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return 0;
    }
}
