package br.com.mestredetalhe.business;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import br.com.mestredetalhe.dao.Repositorio;
import br.com.mestredetalhe.model.Estado;

@ApplicationScoped
public class EstadoBC {

	@Inject
	private Repositorio repositorio;

	@PostConstruct
	public void inicializar() {
		
		Estado estado = new Estado();
		estado.setNome("Ceará");
		estado.setSigla("CE");
		
		repositorio.inserir(estado);

	}

	public List<Estado> selecionar() {
		return repositorio.selecionar(Estado.class);
	}

	public Estado selecionar(Long id) throws EstadoNaoEncontradoException {
		Estado estado = repositorio.selecionar(Estado.class, id);
		if (estado == null) {
			throw new EstadoNaoEncontradoException();
		}
		return estado;
	}

	public Long inserir(Estado estado) throws ValidacaoException {
		validar(estado);
		return repositorio.inserir(estado);
	}

	public void atualizar(Estado estado) throws EstadoNaoEncontradoException, ValidacaoException {
		validar(estado);
		if (!repositorio.atualizar(estado)) {
			throw new EstadoNaoEncontradoException();
		}
	}

	public Estado excluir(Long id) throws EstadoNaoEncontradoException {
		Estado estado = repositorio.excluir(Estado.class, id);
		if (estado == null) {
			throw new EstadoNaoEncontradoException();
		}
		return estado;
	}

	private void validar(Estado estado) throws ValidacaoException {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Estado>> violations = validator.validate(estado);
		if (!violations.isEmpty()) {
			ValidacaoException validacaoException = new ValidacaoException();
			for (ConstraintViolation<Estado> violation : violations) {
				String entidade = violation.getRootBeanClass().getSimpleName();
				String propriedade = violation.getPropertyPath().toString();
				String mensagem = violation.getMessage();

				validacaoException.adicionar(entidade, propriedade, mensagem);
			}
			throw validacaoException;
		}
	}
}