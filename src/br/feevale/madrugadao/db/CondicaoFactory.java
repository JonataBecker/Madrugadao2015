package br.feevale.madrugadao.db;

import br.feevale.madrugadao.ReflactionUtils;

public class CondicaoFactory {

	public static Condicao create(String condicao) {
		try {
			Class c = Class.forName("br.feevale.madrugadao.db.Condicao" + ReflactionUtils.captalize(condicao));
			return (Condicao) c.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
