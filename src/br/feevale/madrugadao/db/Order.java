package br.feevale.madrugadao.db;

public class Order implements FiltroComponent {

	private final String campo;
	private final String ordem;
	
	public Order(String campo, String ordem) {
		this.campo = campo;
		this.ordem = ordem;
	}
	@Override
	public String getSql() {
		return campo + " " + ordem;
	}

}
