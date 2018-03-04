package br.com.mestredetalhe.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Estado extends BaseModel implements Serializable {

	private static final long serialVersionUID = -3064306490724801147L;

	private Long id;

	@NotNull
	@Size(min = 3, max = 100)
	private String nome;

	@NotNull
	@Size(min = 2, max = 10)
	private String sigla;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}