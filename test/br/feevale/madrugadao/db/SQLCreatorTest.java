package br.feevale.madrugadao.db;

import junit.framework.Assert;

import org.junit.Test;

public class SQLCreatorTest {
	
	@Test
	public void create() {
		Entidade entidade = new Tabela("cliente");
		Filtro filtro = new Filtro();
		filtro.addFiltro("idCliente", new CondicaoIgual(), 10);
		SQLCreator sqlCreator = new SQLCreator(entidade, filtro);
		Assert.assertEquals("SELECT * FROM cliente WHERE idCliente = '10' ", 
				sqlCreator.create());
	}

	@Test
	public void createSemFiltro() {
		Entidade entidade = new Tabela("cliente");
		Filtro filtro = new Filtro();
		SQLCreator sqlCreator = new SQLCreator(entidade, filtro);
		Assert.assertEquals("SELECT * FROM cliente", 
				sqlCreator.create());
	}
	
}
