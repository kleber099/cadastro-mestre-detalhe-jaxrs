package br.com.mestredetalhe.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.mestredetalhe.business.CidadeBC;
import br.com.mestredetalhe.business.CidadeNaoEncontradaException;
import br.com.mestredetalhe.business.EstadoBC;
import br.com.mestredetalhe.business.EstadoNaoEncontradoException;
import br.com.mestredetalhe.business.ValidacaoException;
import br.com.mestredetalhe.model.Cidade;
import br.com.mestredetalhe.model.Estado;

@Path("estados")
public class CidadesRS {
	
	@Inject
	private EstadoBC estadosBC;
	
	@Inject
	private CidadeBC cidadeBC;
	
	

	@GET
	@Path("{estado}/cidades")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cidade> selecionarPorEstado(@PathParam("estado") Long est) {
		return cidadeBC.selecionarPorEstado(est);
	}

	@GET
	@Path("cidades/{cidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public Cidade selecionar(@PathParam("cidade") Long idCid) {
		try {
			return cidadeBC.selecionar(idCid);
		} catch (CidadeNaoEncontradaException e) {
			throw new NotFoundException();
		}
	}

	@POST
	@Path("{estado}/cidades")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserir(Cidade body, @PathParam("estado") Long est) {
		
		try {
			Estado estado = estadosBC.selecionar(est);
			body.setEstado(estado);
			
			Long id = cidadeBC.inserir(body);
			String url = "/api/cidades/" + id;
			return Response.status(Status.CREATED).header("Location", url).entity(id).build();
		} catch (ValidacaoException e) {
			return tratarValidacaoException(e);
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NotFoundException();
		}		
	}

	@PUT
	@Path("cidades/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizar(@PathParam("id") Long id, Cidade cidade) {
		try {
			cidade.setId(id);
			cidadeBC.atualizar(cidade);
			return Response.status(Status.OK).entity(id).build();
		} catch (CidadeNaoEncontradaException e) {
			throw new NotFoundException();
		} catch (ValidacaoException e) {
			return tratarValidacaoException(e);
		}
	}

	@DELETE
	@Path("cidades/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluir(@PathParam("id") Long id) {
		try {
			Cidade cidade = cidadeBC.excluir(id);

			return Response.status(Status.OK).entity(cidade).build();
		} catch (CidadeNaoEncontradaException e) {
			throw new NotFoundException();
		}
	}

	private Response tratarValidacaoException(ValidacaoException e) {
		return Response.status(Status.NOT_ACCEPTABLE).entity(e.getErros()).build();
	}
}
