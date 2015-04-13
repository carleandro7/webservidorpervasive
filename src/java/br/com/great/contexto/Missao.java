package br.com.great.contexto;

import java.util.ArrayList;
import java.util.Collection;

public class Missao {

    private String nome;
    private int id;
    private Posicao posicaoInicial;

    private ArrayList<Mecanica> mecanica;

    private Collection<Engajamento> engajamento;

    private ArrayList<Missao> reqMissao;

    private ConfiguracaoMissao configuracaoMissao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Posicao getPosicaoInicial() {
        return posicaoInicial;
    }

    public void setPosicaoInicial(Posicao posicaoInicial) {
        this.posicaoInicial = posicaoInicial;
    }

    public ArrayList<Mecanica> getMecanica() {
        return mecanica;
    }

    public void setMecanica(ArrayList<Mecanica> mecanica) {
        this.mecanica = mecanica;
    }

    public Collection<Engajamento> getEngajamento() {
        return engajamento;
    }

    public void setEngajamento(Collection<Engajamento> engajamento) {
        this.engajamento = engajamento;
    }

    public ArrayList<Missao> getReqMissao() {
        if (reqMissao == null) {
            reqMissao = new ArrayList<Missao>();
        }
        return reqMissao;
    }

    public void setReqMissao(ArrayList<Missao> reqMissao) {
        this.reqMissao = reqMissao;
    }

    public ConfiguracaoMissao getConfiguracaoMissao() {
        return configuracaoMissao;
    }

    public void setConfiguracaoMissao(ConfiguracaoMissao configuracaoMissao) {
        this.configuracaoMissao = configuracaoMissao;
    }

}
