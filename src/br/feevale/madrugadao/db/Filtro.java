package br.feevale.madrugadao.db;

import java.util.ArrayList;
import java.util.List;

public class Filtro {
	
	private final List<FiltroComponent> filtros;	

	private final List<FiltroComponent> orderBy;
	
	public Filtro() {
		this.filtros = new ArrayList<>();
		this.orderBy = new ArrayList<>();
	}
	
	private int getIndexLast() {
		return filtros.size() - 1;
	}
	
	private FiltroComponent getLast() {
		int lastElement = getIndexLast();
		if (lastElement <= 0) {
			return null;
		}
		return filtros.get(lastElement);		
	}
	
	private boolean isLastOperator() {
		FiltroComponent last = getLast();
		if (last == null) {
			return false;
		}
		return last instanceof Operador;
	}
	
	public void addFiltro(String campo, Condicao condicao, Object valor) {
		if (!filtros.isEmpty() && !isLastOperator()) {
			filtros.add(Operador.AND);
		}
		filtros.add(new FiltroItem(campo, condicao, valor));
	}
	
	public void addOperador(Operador operador) {
		if (filtros.isEmpty()) {
			return;
		}
		if (isLastOperator()) {
			filtros.set(getIndexLast(), operador);
			return;
		}
		filtros.add(operador);
	}
	
	public String getSql() {
		StringBuilder sb = new StringBuilder();
		for (FiltroComponent item : filtros) {
			sb.append(item.getSql()).append(" ");
		}
		return sb.toString();
	}
	
	public boolean isPossuiFiltro() {
		return !filtros.isEmpty();
	}
	
	public void addOrderBy(String campo, String ordem) {
		orderBy.add(new Order(campo, ordem));
	}
	
	public boolean isPossuiOrderBy() {
		return !orderBy.isEmpty();
	}
	
	public String getSqlOrder() {
		StringBuilder sb = new StringBuilder();
		for (FiltroComponent item : orderBy) {
			if (orderBy.indexOf(item) > 0) {
				sb.append(", ");
			}
			sb.append(item.getSql());
		}
		return sb.toString();		
	}
}
