package br.com.great.contexto;
public class Objeto {

	private Posicao posicao;

	private CapturarObjeto capturarObjeto;

	private VisualizarObjeto visualizarObjeto;

	private DeixarObjeto deixarObjeto;

    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public CapturarObjeto getCapturarObjeto() {
        return capturarObjeto;
    }

    public void setCapturarObjeto(CapturarObjeto capturarObjeto) {
        this.capturarObjeto = capturarObjeto;
    }

    public VisualizarObjeto getVisualizarObjeto() {
        return visualizarObjeto;
    }

    public void setVisualizarObjeto(VisualizarObjeto visualizarObjeto) {
        this.visualizarObjeto = visualizarObjeto;
    }

    public DeixarObjeto getDeixarObjeto() {
        return deixarObjeto;
    }

    public void setDeixarObjeto(DeixarObjeto deixarObjeto) {
        this.deixarObjeto = deixarObjeto;
    }
        
        
        

}
