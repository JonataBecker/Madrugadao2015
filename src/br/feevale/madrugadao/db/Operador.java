package br.feevale.madrugadao.db;

public enum Operador implements FiltroComponent {

	AND("AND"),
	OR("OR");
	
	private final String op;
	
	private Operador(String op) {
		this.op = op;
	}
	
	@Override
	public String getSql() {
		return op;
	}
}
