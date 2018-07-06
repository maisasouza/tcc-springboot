package tcc;

import java.util.ArrayList;
import java.util.List;

public class RespostaBusca {

	private Long totalEncontrados;
	private List<HitBusca> resultado;
	
	public RespostaBusca() {
		this.resultado = new ArrayList<HitBusca>();
		this.totalEncontrados = new Long(0);
	}
	
	public Long getTotalEncontrados() {
		return totalEncontrados;
	}
	public void setTotalEncontrados(Long totalEncontrados) {
		this.totalEncontrados = totalEncontrados;
	}
	public List<HitBusca> getResultado() {
		return resultado;
	}
	public void setResultado(List<HitBusca> resultado) {
		this.resultado = resultado;
	}
	
}
