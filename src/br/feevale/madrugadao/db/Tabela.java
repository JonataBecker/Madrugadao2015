package br.feevale.madrugadao.db;

public class Tabela implements Entidade {

	private final String nome;

	public Tabela(String nome) {
		this.nome = nome;
	}

	@Override
	public String getNome() {
		return nome;
	}

}
