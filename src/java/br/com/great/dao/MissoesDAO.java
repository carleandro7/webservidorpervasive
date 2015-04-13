/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.dao;

import br.com.great.contexto.Mecanica;
import br.com.great.contexto.Missao;
import br.com.great.contexto.Posicao;
import br.com.great.factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe responsavel realizar toda a interação com banco de dados relacionado
 * com entidade missoes
 *
 * @author carleandro
 */
public class MissoesDAO extends ConnectionFactory {

    private static MissoesDAO instance;

    /**
     *
     * Método responsável por criar uma instancia da classe MissoesDAO
     * (Singleton)
     *
     * @return static
     * @author Carleandro Noleto
     * @since 10/12/2014
     * @version 1.0
     */
    public static MissoesDAO getInstance() {
        if (instance == null) {
            instance = new MissoesDAO();
        }
        return instance;
    }

    public ArrayList<Missao> getMissaoJogo(int jogoConfiguracao) {
        ArrayList<Missao> missoes = new ArrayList<Missao>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conexao = criarConexao();
        try {
            String sql = "SELECT * FROM confimissao_has_missoes "
                    + " LEFT JOIN missoes on (missoes.id = confimissao_has_missoes.missoes_id) "
                    + " WHERE confimissao_has_missoes.confimissao_id = " + jogoConfiguracao;
            pstmt = conexao.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Missao missao = new Missao();
                missao.setId(rs.getInt("missoes.id"));
                missao.setNome(rs.getString("nome"));
                missao.setPosicaoInicial(new Posicao(rs.getDouble("latitude"), rs.getDouble("longitude")));
                ArrayList<Mecanica> mecanicas = new ArrayList<Mecanica>();
                mecanicas = MecanicasDAO.getInstance().getMecSimplesMissao(rs.getInt("missoes.id"), mecanicas);
                mecanicas = MecanicasDAO.getInstance().getMecCompostaMissao(rs.getInt("missoes.id"), mecanicas);
                missao.setMecanica(mecanicas);
                missoes.add(missao);
            }
        } catch (SQLException e) {
            System.out.println("Erro no getMissaoJogo: " + e.getMessage());
        } finally {
            fecharConexao(conexao, pstmt, rs);
        }
        //carrega os requisitos de cada missao
        for (int i = 0; i < missoes.size(); i++) {
            missoes.get(i).setReqMissao(carregaRequisitos(missoes.get(i).getId(), missoes));
            for (int j = 0; j < missoes.get(i).getMecanica().size(); j++) {
                missoes.get(i).getMecanica().get(j).setReqMecanicas(MecanicasDAO.getInstance().mecanicaReq(
                        missoes.get(i).getMecanica().get(j).getId(), missoes.get(i).getMecanica()));
                
            }
        }
        return missoes;
    }

    private ArrayList<Missao> carregaRequisitos(int missao_id_requisitos, ArrayList<Missao> missoes) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conexao = criarConexao();
        ArrayList<Missao> missoes_requisitos = new ArrayList<Missao>();
        try {
            String sql = "SELECT * FROM reqmissao WHERE reqmissao.missoes_id = " + missao_id_requisitos;
            pstmt = conexao.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int req_id = rs.getInt("req_id");
                missoes_requisitos.add(getMissao(req_id, missoes));
            }
        } catch (SQLException e) {
            System.out.println("Erro no getReqMissao: " + e.getMessage());
        } finally {
            fecharConexao(conexao, pstmt, rs);
        }
        return missoes_requisitos;
    }

    private Missao getMissao(int missao_id, ArrayList<Missao> missoes) {
        for (int i = 0; i < missoes.size(); i++) {
            if (missoes.get(i).getId() == missao_id) {
                return missoes.get(i);
            }
        }
        return null;
    }

}
