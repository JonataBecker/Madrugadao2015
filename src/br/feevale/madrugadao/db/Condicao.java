package br.feevale.madrugadao.db;

public interface Condicao {

	public String getOperador();
	
	public Object formatValue(Object object);
}
