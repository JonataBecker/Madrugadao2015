package br.feevale.madrugadao.db;

public class View implements Entidade {

	private final String nome;

	public View(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return "\"SYS_BIC\".\"p1941727594trial." + nome;
	}

}
