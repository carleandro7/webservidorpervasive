package br.com.great.contexto;
import java.util.ArrayList;
import java.util.Collection;

public class ConfiguracaoMissao {

	private ArrayList<Missao> missao;

	private ArrayList<Grupo> grupo;

	private Jogo jogo;

    public ArrayList<Missao> getMissao() {
        return missao;
    }

    public void setMissao(ArrayList<Missao> missao) {
        this.missao = missao;
    }

    public ArrayList<Grupo> getGrupo() {
        return grupo;
    }

    public void setGrupo(ArrayList<Grupo> grupo) {
        this.grupo = grupo;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }
        
        

}
