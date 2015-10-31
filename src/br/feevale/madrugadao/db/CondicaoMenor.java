package br.feevale.madrugadao.db;

public class CondicaoMenor implements Condicao {
	
	@Override
	public String getOperador() {
		return "<";
	}
	
	@Override
	public Object formatValue(Object object) {
		return object;
	}
	
}
