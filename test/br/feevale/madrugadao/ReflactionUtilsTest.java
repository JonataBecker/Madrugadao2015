package br.feevale.madrugadao;

import static junit.framework.Assert.*;

import org.junit.Test;

public class ReflactionUtilsTest {

	@Test
	public void captalize() {
		assertEquals("Igual", ReflactionUtils.captalize("IGUAL"));
		assertEquals("Maior", ReflactionUtils.captalize("MAIOR"));
		assertEquals("", ReflactionUtils.captalize(""));
		assertEquals("   ", ReflactionUtils.captalize("   "));
		assertNull(ReflactionUtils.captalize(null));
	}
}
