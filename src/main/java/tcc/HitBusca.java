package tcc;

public class HitBusca {

	private String mensagemId;
	private Float score;
	private String id;
	
	public HitBusca(String id, String mensagemId, Float score) {
		this.id = id;
		this.mensagemId = mensagemId;
		this.score = score;
	}
	
	public String getMensagemId() {
		return mensagemId;
	}
	public void setMensagemId(String mensagemId) {
		this.mensagemId = mensagemId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	
	
}
