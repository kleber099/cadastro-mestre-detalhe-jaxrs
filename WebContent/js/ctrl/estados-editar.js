$(function() {
   var id = obterParametroDaUrlPorNome('id');
   if (id) {
       EstadosProxy.selecionar(id)
           .done(obterOk)
           .fail(tratarErro);
       
       CidadesProxy.selecionarTodos(id)
       .done(buscarOkCidades);
   }
   
   $("#salvar").click(function(event) {
	  limparMensagensErro();

      var estado = {
         id : $("#id").val(),
         nome : $("#nome").val(),
         sigla : $("#sigla").val()
      };

      if (estado.id) {
          EstadosProxy.atualizar(estado.id, estado)
             .done(atualizarOk)
             .fail(tratarErro);
	   } else {
	      EstadosProxy.inserir(estado)
	         .done(inserirOk)
	         .fail(tratarErro);
	   }
   });
   
   $("#excluir").click(function(event) {
	      var id = $("#id").val();
	      EstadosProxy.excluir(id)
	      	.done(excluirOk)
	      	.fail(tratarErro);
	   });

});

function inserirOk(data, textStatus, jqXHR) {
   $("#id").val(data);
   $("#global-message")
      .addClass("alert-success")
      .text("Estado criado com suceso")
      .show();
}
function obterParametroDaUrlPorNome(name){
   name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
   var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), 
                   results = regex.exec(location.search);
   return results === null ? "" : 
      decodeURIComponent(results[1].replace(/\+/g, " "));
}

function obterOk(data) {
	$("#id").val(data.id);
   $("#id-input").val(data.id);
   $("#nome").val(data.nome);
   $("#sigla").val(data.sigla);
}

function atualizarOk(data, textStatus, jqXHR) {
   $("#global-message")
       .addClass("alert-success")
       .text("Estado atualizado com sucesso.")
       .show();
}

function excluirOk(data, textStatus, jqXHR) {
   $("#id").val(null);
   $("#id-input").val(null);
   $("#nome").val(null);
   $("#sigla").val(null);
   
   $("#global-message").addClass("alert-success")
      .text("Usuário excluído com sucesso.")
      .show();
}


function limparMensagensErro() {
   /* Limpa as mensagens de erro */
   $("#global-message").removeClass("alert-danger alert-success")
      .empty().hide();
   $(".control-label").parent().removeClass("has-success");
   $(".text-danger").parent().removeClass("has-error");
   $(".text-danger").hide();
}

function tratarErro(request) {
   switch (request.status) {
   case 406:
      $("form input").each(function() {
         var id = $(this).attr("id"); var message = null;
         $.each(request.responseJSON, function(index, value) {
            if (id == value.propriedade) { message = value.mensagem; }
         });
         if (message) {
            $("#" + id).parent().addClass("has-error");
            $("#" + id + "-message").html(message).show();
            $(this).focus();
         } else {
            $("#" + id).parent().removeClass("has-error");
            $("#" + id + "-message").hide();
         }
      });
      $("#global-message").addClass("alert-danger")
         .text("Verifique erros no formulário!").show();
      break;
   case 404:
      $("#global-message")
          .addClass("alert-danger")
          .text("O registro solicitado não foi encontrado!")
          .show();
      break;
   default:
      $("#global-message")
         .addClass("alert-danger")
         .text("Erro inesperado.")
         .show();
      break;
   }
}

function buscarOkCidades(cidades) {
	   var corpo = $('#cidades tbody');
	   corpo.empty();
	   if (cidades.length) {
	      $.each(cidades, function(i, cidade) {
	         corpo.append(
	            $('<tr>').append(
	               $('<td>').text(cidade.id),
	               $('<td>').text(cidade.nome),
	               $('<td>').text(cidade.data),
	               $('<td>').text(cidade.populacao),
	               $('<td>').text(cidade.pib),
	               $('<td>').append(
	                       $('<a>').attr('href',
	                             'cidades-editar.html?id=' + cidade.id)
	                          .text("Editar")))
	            );
	      });
	   } else {
	      corpo.append(
	         $('<tr>').append(
	            $('<td>')
	               .attr('colspan', 4)
	               .text('Nenhum registro encontrado!')
	         )
	      );
	   }
	}