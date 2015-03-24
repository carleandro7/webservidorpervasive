/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.great.dao;

import br.com.great.factory.ConnectionFactory;
import br.com.great.model.Jogo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * Classe responsavel realizar toda a interação com banco de dados relacionado com entidade jogo
 * @author carleandro
 */
public class JogosDAO extends ConnectionFactory{
    
    private static JogosDAO instance;
                /**
	 * 
	 * Método responsável por criar uma instancia da classe JogosDAO (Singleton)
	 *
	 * @return static
	 * @author Carleandro Noleto
	 * @since 27/11/2014
	 * @version 1.0
	 */
	public static JogosDAO getInstance(){
		if(instance == null)
			instance = new JogosDAO();
		return instance;
	}
	
	/**
	 * 
	 * Método responsável por listar todos os pjogos do banco
	 *
	 * @return JSONArray lista de jogos
	 * @author Carleandro Noleto
	 * @since 27/11/2014
	 * @version 1.0
	 */
	public JSONArray listarTodos(){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray jogos = null;
		Connection conexao = criarConexao();
		try {
                        jogos= new JSONArray();
                        pstmt = conexao.prepareStatement("select * from jogos order by id");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				JSONObject jogo = new JSONObject();
                                jogo.put("id",rs.getInt("id"));
                                jogo.put("nome",rs.getString("nome"));
                                jogo.put("icone",rs.getString("icone"));
                                jogo.put("longitude",rs.getString("longitude"));
                                jogo.put("latitude",rs.getString("latitude"));
				jogos.put(jogo);
			}
			
		} catch (SQLException | JSONException e) {
			System.out.println("Erro ao listar todos os jogos: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return jogos;
	}
        /**
	 * Método responsável por listar todos os jogos perto de um local com distancia definida
         *
         * @param latitude String
         * @param longitude String
         * @param distancia String
	 * @return JSONArray lista de jogos
	 * @author Carleandro Noleto
	 * @since 27/11/2014
	 * @version 1.0
	 */
        public JSONArray getJogos(double latitude, double longitude, double distancia){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray jogos = null;
		Connection conexao = criarConexao();
		try {
                        jogos= new JSONArray();
                        //distancia em KM
                        String sql = 
                                "SELECT  ((3956 * 2 * ASIN(SQRT(POWER(SIN((abs("+latitude+") - abs(dest.latitude)) *  "
                                + " pi()/180 / 2),2) + COS(abs("+latitude+") * pi()/180 ) * COS(abs(dest.latitude) * pi()/180) "
                                + "* POWER(SIN((abs("+longitude+") - abs(dest.longitude)) * 	pi()/180 / 2), 2)))) * 1.609344) as "
                                + "distancia, nome, id, icone FROM jogos dest having distancia < "+distancia+" ORDER BY distancia limit 100";
                        pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				JSONObject jogo = new JSONObject();
                                jogo.put("id",rs.getInt("id"));
                                jogo.put("nome",rs.getString("nome"));
                                jogo.put("icone",rs.getString("icone"));
				jogos.put(jogo);
			}
			
		} catch (SQLException | JSONException e) {
			System.out.println("Erro ao listar todos os jogos em uma distancia em KM: " + e.getMessage());
                } finally {
			fecharConexao(conexao, pstmt, rs);
		}
		return jogos;
	}
        
    /**
     * Lista de todos os jogos que ainda estao sendo executados com status 1
     * @return JSONArray lista de todos os jogos
     */
    public ArrayList<Jogo> getJogosExecutando() {
        ArrayList<Jogo> jogos= new ArrayList<Jogo>();
        PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conexao = criarConexao();
	try {
            String sql = "select * from jogos where status="+1;
            pstmt = conexao.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Jogo jogo = new Jogo();
                jogo.setId(rs.getInt("id"));
                jogo.setNome(rs.getString("nome"));
                jogo.setIcone(rs.getString("icone"));
		jogo.setLatitude(rs.getString("latitude"));
                jogo.setLongitude(rs.getString("longitude"));
                jogo.setUser_id(rs.getInt("user_id"));
                jogo.setNomeficticio(rs.getString("nomeficticio"));
                jogo.setStatus(rs.getInt("status"));
                jogo.setUser_id(rs.getInt("user_id"));
                jogos.add(jogo);
            }
        } catch (SQLException e) {
            System.out.println("Erro no getJogosExecutando: " + e.getMessage());
        } finally {
            fecharConexao(conexao, pstmt, rs);
	}
        return jogos;
    }
        
}
