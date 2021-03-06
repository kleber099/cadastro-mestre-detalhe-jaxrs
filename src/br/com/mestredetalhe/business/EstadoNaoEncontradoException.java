package br.com.mestredetalhe.business;

/**
 * Classe de excecao disparada pela camada de negocio.
 * @author Fabio Barros
 */
public class EstadoNaoEncontradoException extends Exception {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1071896537277884578L;

	public EstadoNaoEncontradoException() {
		super("Login ou senha invalidos!");
	}
}
