package br.feevale.madrugadao.db;

import java.util.Map;

public class FiltroFactory {

	private static final String CAMPO = "filtro.campo";
	private static final String CONDICAO = "filtro.condicao";
	private static final String VALOR = "filtro.valor";
	private static final String OPERADOR = "filtro.operador";
	
	public static Filtro create(Map<String, String[]> parameter) {
		return new Montador(parameter).montaFiltro();
	}
	
	private static class Montador {
		
		private final String[] campos;
		private final String[] condicao;
		private final String[] valor;
		private final String[] operador;
		
		public Montador(Map<String, String[]> parameter) {
			this.campos = parameter.get(CAMPO);
			this.condicao = parameter.get(CONDICAO);
			this.valor = parameter.get(VALOR); 
			this.operador = parameter.get(OPERADOR); 
		}
		
		private boolean isPossuiFiltro() {
			return campos != null && condicao != null && valor != null;
		}
		
		private boolean isAtributoDivergente() {
			return campos.length != condicao.length || 
					campos.length != valor.length || 
					condicao.length != valor.length;
		}
		
		private boolean isPossuiOperador(int i) {
			return operador != null && 
					operador.length == (campos.length - 1) &&
					operador.length > i;
		}
		
		private Filtro montaFiltro() {
			if (!isPossuiFiltro() || isAtributoDivergente()) {
				return new Filtro();
			}
			Filtro filtro = new Filtro();
			for (int i = 0; i < campos.length; i++) {
				filtro.addFiltro(campos[i], CondicaoFactory.create(condicao[i]), valor[i]);
				if (isPossuiOperador(i)) {
					Operador op = Operador.valueOf(operador[i]);
					if (op != null) {
						filtro.addOperador(op);
					}
				}
			}
			return filtro;
		}
	}
	
}
