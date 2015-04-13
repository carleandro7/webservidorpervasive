package br.com.great.contexto;

import java.util.ArrayList;
import java.util.Collection;

public class Jogo {

    private int id, jogador_iniciou, status;
    private String nome, icone, nomeficticio, user;
    private Posicao posicao;

    private ArrayList<DadosPersonagem> dadosPersonagem;

    private ArrayList<ConfiguracaoMissao> configuracaoMissao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJogador_iniciou() {
        return jogador_iniciou;
    }

    public void setJogador_iniciou(int jogador_iniciou) {
        this.jogador_iniciou = jogador_iniciou;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNomeficticio() {
        return nomeficticio;
    }

    public void setNomeficticio(String nomeficticio) {
        this.nomeficticio = nomeficticio;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public ArrayList<DadosPersonagem> getDadosPersonagem() {
        return dadosPersonagem;
    }

    public void setDadosPersonagem(ArrayList<DadosPersonagem> dadosPersonagem) {
        this.dadosPersonagem = dadosPersonagem;
    }

    public ArrayList<ConfiguracaoMissao> getConfiguracaoMissao() {
        return configuracaoMissao;
    }

    public void setConfiguracaoMissao(ArrayList<ConfiguracaoMissao> configuracaoMissao) {
        this.configuracaoMissao = configuracaoMissao;
    }

}
