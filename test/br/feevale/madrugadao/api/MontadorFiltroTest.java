package br.feevale.madrugadao.api;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import br.feevale.madrugadao.db.FiltroFactory;

public class MontadorFiltroTest {

	@Test
	public void montaFiltro() {
		Map<String, String[]> parameter = new HashMap<>();
		parameter.put("filtro.campo", new String[]{"idCliente", "razaoSocial", "fantasia"});
		parameter.put("filtro.condicao", new String[]{"IGUAL", "MAIOR", "MENOR"});
		parameter.put("filtro.valor", new String[]{"10", "15", "20"});
		Assert.assertEquals(FiltroFactory.create(parameter).getSql(), 
				"idCliente = '10' AND razaoSocial > '15' AND fantasia < '20' ");
	}
	
	@Test
	public void montaFiltroOperador() {
		Map<String, String[]> parameter = new HashMap<>();
		parameter.put("filtro.campo", new String[]{"idCliente", "razaoSocial", "fantasia"});
		parameter.put("filtro.condicao", new String[]{"IGUAL", "MAIOR", "MENOR"});
		parameter.put("filtro.valor", new String[]{"10", "15", "20"});
		parameter.put("filtro.operador", new String[]{"OR", "AND"});
		Assert.assertEquals(FiltroFactory.create(parameter).getSql(), 
				"idCliente = '10' OR razaoSocial > '15' AND fantasia < '20' ");
	}
	
	@Test
	public void motaFiltroVazio() {
		Map<String, String[]> parameter = new HashMap<>();
		Assert.assertEquals(FiltroFactory.create(parameter).getSql(), "");
	}
	
}
