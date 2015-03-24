/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.dao;

import br.com.great.factory.ConnectionFactory;
import br.com.great.model.MecComposta;
import br.com.great.model.MecSimples;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author carleandro
 */
public class MecCompostaDAO  extends ConnectionFactory{
    
        private static MecCompostaDAO instance;
                /**
	 * 
	 * Método responsável por criar uma instancia da classe MecCompostaDAO (Singleton)
	 *
	 * @return static
	 * @author Carleandro Noleto
	 * @since 10/12/2014
	 * @version 1.0
	 */
	public static MecCompostaDAO getInstance(){
		if(instance == null)
			instance = new MecCompostaDAO();
		return instance;
	}
        
        
        public ArrayList<MecComposta> getMecaniaMissao(int missao_id){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MecComposta> mecanicas = null;
		Connection conexao = criarConexao();
		try {
                        mecanicas= new ArrayList<MecComposta>();
                         String sql = "SELECT * FROM meccomposta "
                                 + " LEFT JOIN mecanica ON(mecanica.id = meccomposta.mecanica_id) "
                                 + " WHERE mecanica.missoes_id = "+missao_id+" AND mecanica.tipo=1"; 
                
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
                
			while(rs.next()){
				MecComposta mecanica = new MecComposta();
                                mecanica.setMeccomposta_id(rs.getInt("meccomposta.id"));
                                mecanica.setNome(rs.getString("mecanica.nome"));
                                mecanica.setTipo(rs.getInt("mecanica.tipo"));
                                mecanica.setMissoes_id(rs.getInt("mecanica.missoes_id"));
                                mecanica.setMecanica_id(rs.getInt("mecanica.id"));
                                mecanica.setListMecSimples(MecSimplesDAO.getInstance().getMecaniaMecComposta(mecanica.getMeccomposta_id()));
                                mecanicas.add(mecanica);
			}
			
                        	
		} catch (SQLException e) {
			System.out.println("Erro ao listar dados de uma mecanica: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return mecanicas;
	}

    public MecComposta getMecaniaMissao(int mecanica_id, int missao_id){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conexao = criarConexao();
		try {                        
                         String sql = "SELECT * FROM meccomposta "
                                 + " LEFT JOIN mecanica ON(mecanica.id = meccomposta.mecanica_id) "
                                 + " WHERE mecanica.missoes_id = "+missao_id+" AND mecanica.id="+mecanica_id; 
                
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
                
			while(rs.next()){
				MecComposta mecanica = new MecComposta();
                                mecanica.setMeccomposta_id(rs.getInt("meccomposta.id"));
                                mecanica.setNome(rs.getString("mecanica.nome"));
                                mecanica.setTipo(rs.getInt("mecanica.tipo"));
                                mecanica.setMissoes_id(rs.getInt("mecanica.missoes_id"));
                                mecanica.setMecanica_id(rs.getInt("mecanica.id"));
                                mecanica.setRequisitos(MecSimplesDAO.getInstance().listRequisitos(mecanica.getMecanica_id()));
                                mecanica.setListMecSimples(MecSimplesDAO.getInstance().getMecaniaMecComposta(mecanica.getMeccomposta_id()));
                                return mecanica;
			}
			
                        	
		} catch (SQLException e) {
			System.out.println("Erro ao listar dados de uma mecanica: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return null;
	}

}
