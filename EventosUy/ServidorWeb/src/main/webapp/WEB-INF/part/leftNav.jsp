<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.List" %>
<%@ page import="webservice.*" %>

<%

List<String>  cates = (List<String> ) request.getAttribute("listaCategorias");

DtUsuario usuario = (DtUsuario) session.getAttribute("datosUsuario");
boolean esOrganizador = (usuario != null) && (usuario instanceof DtOrganizador);
boolean esAsistente = (usuario != null) && (usuario instanceof DtAsistente);
%>

<jsp:include page="routes.jsp"></jsp:include>




<div class=" col-xl-2 col-lg-2 col-md-3 col-sm-9 col-11 mb-3 mx-auto" style="margin-left:1px; margin-right:1px;">

    <% if (esOrganizador) { %>
    
    <div class="row c2 cLetraRelleno text-center rt">
		<h5 class="negrita mt-1">Mi Perfil</h5>
    </div>
		<div class="row cLetra  rb fondo fuente1">
	    <div class="col-12"><a class="sinSubrayado" href="SrvEvento?destino=altaEvento">Alta de Evento</a></div>
		<div class="col-12"><a class="sinSubrayado" href="SrvEdicion?destino=altaEdicion">Alta de Edición</a></div>
		<div class="col-12"><a class="sinSubrayado" href="SrvUsuario?destino=altaInstitucion">Alta de Institución</a></div>
		<div class="col-12"><a class="sinSubrayado" href="SrvPatrocinio?destino=altaPatrocinio">Alta de Patrocinio</a></div>
		<div class="col-12"><a class="sinSubrayado" href="SrvTipoRegistro?destino=altaTipoRegistro">Alta de Tipo de Registro</a></div>
		<div class="col-12"><a class="sinSubrayado" href="SrvEvento?destino=finEvento">Finalizar evento</a></div>
		<div class="col-12"><a class="sinSubrayado" href="SrvEdicion?destino=misEdiciones&amp;nickOrg=<%= java.net.URLEncoder.encode(usuario.getNickname(), "UTF-8") %>">Mis ediciones</a>
</div>
	</div>
	<% } %>
	
	<% if (esAsistente) { %>
	
	<div class="row c2 cLetraRelleno text-center rt">
		<h5 class="negrita mt-1">Mi Perfil</h5>
	</div>
		<div class="row cLetra  rb fondo fuente1">
		<div class="col-12"><a class="sinSubrayado" href="listaRegistros">Mis registros</a></div>
		<div class="col-12"><a class="sinSubrayado" href="srvCompra">Registrarme a un evento</a></div>
		<div class="col-12"><a class="sinSubrayado" href="SrvUsuario?destino=editarPerfil">Modificar mis datos</a></div>
	</div>
	<% } %>
  
	<div class="row c2 mt-5 cLetraRelleno text-center rt">
		<h5 class="negrita mt-1">Categorías</h5>
	</div>
	<div class="row cLetra  rb fondo fuente1">
	<% if(cates != null){ for(String cat:cates){%>
		<div class="col-12"><a class="sinSubrayado" href="SrvEvento?destino=listarEventosCategoria&categoria=<%=cat%>"><%=cat%></a></div>
	    <%}} %>
	</div>
	<div class="row c2 cLetraRelleno text-center soloTituloLink rt mt-5">
        <h5 class="negrita mt-1">
           <a href="SrvUsuario?destino=listarUsuarios" class="cLetraRelleno soloTituloLink sinSubrayado">Usuarios</a>
        </h5>
    </div>	
</div>


