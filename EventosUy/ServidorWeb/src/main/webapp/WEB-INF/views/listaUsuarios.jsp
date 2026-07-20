<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="webservice.DtUsuario"%>
<%@ page import="webservice.*"%>
<%

	@SuppressWarnings("unchecked")

	Map<String, DtUsuario> usuarios = (Map<String, DtUsuario>) request.getAttribute("usuarios");
	boolean hayUsuarios = (usuarios!=null && usuarios.size()>0)? true: false;
   
	Map<String,DtUsuario> seguidos = new HashMap<String,DtUsuario>();
	HttpSession sesion = request.getSession(false);
	String nicklog = null;
    boolean logeado = false;
    if (sesion != null) {
        String estadoSesion = (String) sesion.getAttribute("estadoSesion");
        if ("logeado".equals(estadoSesion)) {
            logeado = true;
            DtUsuario usuario = (DtUsuario)sesion.getAttribute("datosUsuario");
            nicklog = usuario.getNickname();
            seguidos = (Map<String,DtUsuario>)request.getAttribute("seguidos");
          }
    }

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Eventos Uy</title>
</head>
<body>
	<jsp:include page="../part/nav.jsp"></jsp:include>
	<div class="container-fluid text-center mt-3 mx-auto ">    
		<div class="row">
		    <jsp:include page="../part/leftNav.jsp"></jsp:include>
			<div class="col-11 col-xl-9 col-lg-9 col-md-8 col-sm-9 mx-auto">
				<div class="row">
					<div class="row rt c2 cLetraRelleno text-start mx-auto ">
						<h5 class="negrita mt-1 ms-1 ">Usuarios:</h5>
					</div>
					<% if(hayUsuarios){ %>
						<% for(DtUsuario usu : usuarios.values()){ %>
						<div class="col-md-9">
							<div class="row text-start mt-4 mx-auto bordesDesc">
						   	<div class="col-xl-3 col-lg-3 col-md-4 col-12 mb-1 mx-auto">
								<a class="link" href="SrvUsuario?destino=detalleUsuario&nick=<%=usu.getNickname()%>">
								<% String rutaImagen =(usu.getImagen().equals(""))?"defaultUser.png":usu.getImagen();  %>
						   			<img src="media/users/<%=rutaImagen%>" class="rounded float-center img-fluid align-middle" alt="Imagen no disponible" width="400" height="400">
								</a>
							</div>
						   	<div class="col-xl-9 col-lg-9 col-md-8 bordesDesc fondo rounded itembox ">
						   		<h4 class="subt"><%=usu.getNombre() %></h4>
						   		<p class="desc">Nickname:  <%=usu.getNickname() %>
						   		<p class="desc">Correo:  <%=usu.getCorreo() %>
						   		<p><a class="link desc" href="SrvUsuario?destino=detalleUsuario&nick=<%=usu.getNickname()%>">ver información</a></p>
						   		<% if(logeado){ %>
						   		    <% String seguidor = usu.getNickname(); %>
						   		     <%if (!usu.getNickname().equals(nicklog)){%>
						   			 <% if(seguidos != null && seguidos.containsKey(seguidor)){ %>						   		   
							   			<p><a class="link desc btn btn-sm btn-outline-warning" style="width:300px;" href="SrvUsuario?destino=dejarDeSeguirUsuario&nick=<%=usu.getNickname()%>">dejar de seguir usuario</a></p>
						   			<% }else{ %>
						   				<p><a class="link desc btn btn-sm btn-outline-success" style="width:300px;" href="SrvUsuario?destino=comenzarASeguirUsuario&nick=<%=usu.getNickname()%>">comenzar a seguir usuario</a></p>
						   			<% } %>
						   		<% } %>
						   		<% } %>
						    	</div>
							</div>
						</div>
				   <% } %>	
				 	<% } %>			
				</div>
			</div>
		</div>
	</div>
</body>
</html>