package br.com.mestredetalhe.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import br.com.mestredetalhe.dao.Repositorio;
import br.com.mestredetalhe.model.Cidade;
import br.com.mestredetalhe.model.Estado;

@ApplicationScoped
public class CidadeBC {
	
	@Inject
	private Repositorio repositorio;

	@PostConstruct
	public void inicializar() {
		
		Estado estado = new Estado();
		estado.setId(1L);
		estado.setNome("Ceará");
		estado.setSigla("CE");
		
		Calendar data = Calendar.getInstance();
		
		Cidade cidade = new Cidade();
		cidade.setNome("Fortaleza");
		data.set(1798, 9, 12);
		cidade.setData(data.getTime());
		cidade.setPopulacao(123456L);
		cidade.setPib(12.5);
		cidade.setEstado(estado);
		
		repositorio.inserir(cidade);
		
	}

	public List<Cidade> selecionarPorEstado(Long idEstado) {
		List<Cidade> cidades = repositorio.selecionar(Cidade.class);
		List<Cidade> cidadesEstado = new ArrayList<Cidade>();
		
		for (Cidade cidade : cidades) {
			Estado estado = cidade.getEstado();
			if (estado.getId().equals(idEstado)) {
				cidadesEstado.add(cidade);
			}
		}
		
		return cidadesEstado;
	}

	public Cidade selecionar(Long id) throws CidadeNaoEncontradaException {
		Cidade cidade = repositorio.selecionar(Cidade.class, id);
		if (cidade == null) {
			throw new CidadeNaoEncontradaException();
		}
		return cidade;
	}

	public Long inserir(Cidade cidade) throws ValidacaoException {
		validar(cidade);
		return repositorio.inserir(cidade);
	}

	public void atualizar(Cidade cidade) throws CidadeNaoEncontradaException, ValidacaoException {
		validar(cidade);
		if (!repositorio.atualizar(cidade)) {
			throw new CidadeNaoEncontradaException();
		}
	}

	public Cidade excluir(Long id) throws CidadeNaoEncontradaException {
		Cidade cidade = repositorio.excluir(Cidade.class, id);
		if (cidade == null) {
			throw new CidadeNaoEncontradaException();
		}
		return cidade;
	}

	private void validar(Cidade cidade) throws ValidacaoException {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Cidade>> violations = validator.validate(cidade);
		if (!violations.isEmpty()) {
			ValidacaoException validacaoException = new ValidacaoException();
			for (ConstraintViolation<Cidade> violation : violations) {
				String entidade = violation.getRootBeanClass().getSimpleName();
				String propriedade = violation.getPropertyPath().toString();
				String mensagem = violation.getMessage();

				validacaoException.adicionar(entidade, propriedade, mensagem);
			}
			throw validacaoException;
		}
	}

}
