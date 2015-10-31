package br.feevale.madrugadao.db;

public class CondicaoContem implements Condicao {

	@Override
	public String getOperador() {
		return "LIKE";
	}
	
	@Override
	public Object formatValue(Object object) {
		return "%" + object + "%";
	}
}
