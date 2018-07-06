package hello;

public class HitBusca {

	private String mensagemId;
	private String score;
	private String id;
	
	public HitBusca(String id, String mensagemId, String score) {
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
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	
}
