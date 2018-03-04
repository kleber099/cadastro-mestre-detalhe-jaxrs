package br.com.mestredetalhe.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Cidade extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2953941563349341774L;

	private Long id;
	
	@NotNull
	@Size(min = 3, max = 100)
	private String nome;
	
	@Past
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date data;
	
	@NotNull
	private Long populacao;
	
	@NotNull
	private Double pib;
	
	private Estado estado;
	
	public Cidade() {
		super();
	}

	public Cidade(Long id) {
		this();
		setId(id);
	}

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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getPopulacao() {
		return populacao;
	}

	public void setPopulacao(Long populacao) {
		this.populacao = populacao;
	}

	public Double getPib() {
		return pib;
	}

	public void setPib(Double pib) {
		this.pib = pib;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
