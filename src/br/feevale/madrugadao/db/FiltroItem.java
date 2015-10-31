package br.feevale.madrugadao.db;

public class FiltroItem implements FiltroComponent {

	private final String campo;
	private final Condicao condicao;
	private final Object valor;

	public FiltroItem(String campo, Condicao condicao, Object valor) {
		this.campo = campo;
		this.condicao = condicao;
		this.valor = valor;
	}

	public String getCampo() {
		return campo;
	}

	public Condicao getCondicao() {
		return condicao;
	}

	public Object getValor() {
		return valor;
	}
	
	@Override
	public String getSql() {
		StringBuilder sb = new StringBuilder();
		sb.append(campo);
		sb.append(" ").append(condicao.getOperador()).append(" ");
		sb.append("'").append(condicao.formatValue(valor)).append("'");
		return sb.toString();
	}

}
