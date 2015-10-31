package br.feevale.madrugadao.db;

public class CondicaoIgual implements Condicao {

	@Override
	public Object formatValue(Object value) {
		return value;
	}
	
	@Override
	public String getOperador() {
		return "=";
	}
}
