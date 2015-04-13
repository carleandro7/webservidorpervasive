package br.com.great.contexto;

import java.util.Collection;

public class Grupo {

    private String nome;
    private int id;

    private Collection<Engajamento> engajamento;

    private Collection<ConfiguracaoMissao> configuracaoMissao;

    private Collection<Jogador> jogador;

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

    public Collection<Engajamento> getEngajamento() {
        return engajamento;
    }

    public void setEngajamento(Collection<Engajamento> engajamento) {
        this.engajamento = engajamento;
    }

    public Collection<ConfiguracaoMissao> getConfiguracaoMissao() {
        return configuracaoMissao;
    }

    public void setConfiguracaoMissao(Collection<ConfiguracaoMissao> configuracaoMissao) {
        this.configuracaoMissao = configuracaoMissao;
    }

    public Collection<Jogador> getJogador() {
        return jogador;
    }

    public void setJogador(Collection<Jogador> jogador) {
        this.jogador = jogador;
    }

}
