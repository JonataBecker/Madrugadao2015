package br.feevale.madrugadao.db;

import junit.framework.Assert;

import org.junit.Test;

public class FiltroTest {
	
	@Test
	public void addFiltro() {
		Filtro filtro = new Filtro();
		filtro.addOperador(Operador.OR);
		filtro.addFiltro("idCliente", new CondicaoIgual(), "55");
		filtro.addFiltro("razaoSocial", new CondicaoMaior(), "10");
		filtro.addFiltro("fantasia", new CondicaoMenor(), "666");
		filtro.addFiltro("estado", new CondicaoContem(), "ola");
		filtro.addOperador(Operador.OR);
		filtro.addFiltro("estado", new CondicaoContem(), "ola");
		Assert.assertEquals("idCliente = '55' AND razaoSocial > '10' AND fantasia < '666' AND estado LIKE '%ola%' OR estado LIKE '%ola%' ", 
				filtro.getSql());
	}
}
