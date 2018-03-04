var CidadesProxy = {
   url : "api/estados",

   inserir : function(idEstado, cidade) {
      return $.ajax({
          type : "POST",
          url : this.url + "/" + idEstado + "/cidades",
          data : JSON.stringify(cidade),
          contentType : "application/json"
      });
   },
   
   atualizar : function(id, estado) {
      return $.ajax({
         type : "PUT",
         url : this.url + "/cidades/" + id,
         data : JSON.stringify(estado),
         contentType : "application/json"
      });
   },
   
   excluir : function(id) {
      return $.ajax({
         type : "DELETE",
         url : this.url + "/cidades/" + id
      });
   },
	   
   selecionar : function(id) {
      return $.ajax({
         type : "GET",
         url : this.url + "/cidades/" + id
      });
   },

   selecionarTodos : function(idEstado) {
      return $.ajax({
         type : "GET",
         url : this.url + "/" + idEstado + "/cidades"
      });
   }
   
};