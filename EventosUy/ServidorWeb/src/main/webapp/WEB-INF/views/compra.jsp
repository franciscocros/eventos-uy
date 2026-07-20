<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="webservice.DtEvento"%>
<%@ page import="webservice.DtEdicion"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="webservice.DtTipoRegistro"%>
    <%

	@SuppressWarnings("unchecked")

    List<DtEvento> Eventos = (List<DtEvento>) request.getAttribute("eventos");
    String selEvento = (String) request.getAttribute("eventoSel");
	boolean hayEventos = (Eventos!=null && Eventos.size()>0)? true: false;
	List<DtEdicion> Ediciones = (List<DtEdicion>) request.getAttribute("ediciones");
    String selEdicion = (String) request.getParameter("edicionSelect");
	boolean hayEdiciones = (Ediciones!=null && Ediciones.size()>0)? true: false;
	
	Map<String,List<DtTipoRegistro> > tipoRegistro =( Map<String,List<DtTipoRegistro> >)   request.getAttribute("tregistros");
	//System.out.println("||" + tipoRegistro);
	int ide = 0;
	String error = (String) request.getAttribute("Error");
	String exito = (String) request.getAttribute("ok");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Principal</title>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/estilo.css">

</head>
<body>
	 <jsp:include page="../part/nav.jsp"></jsp:include>
	     <div class="container mt-3 text-center ">
      <div class="row justify-content-md-center">
      	<div class="col col-lg-5 fondosuave rt rb">
			<div class="row text-center c2 cLetraRelleno  rts">
				<h5 class="negrita">Compra de entrada</h5>
			</div>
			<form id="formUsuario" method="post" class="needs-validation" novalidate>

				<div class="row text-start" id="usuario">
				  <div class="mb-3">
				  	<% if (error!=null){ %><div id="errorDiv" class="alert alert-danger mt-2" > <%= error.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "") %></div><%} %>
				  	<% if (exito!=null){ %><div id="successDiv" class="alert alert-success mt-2" > <%= exito %></div><%} %>
				    <label for="nickname" class="altaUsuLetra text-start">Evento:</label>
				    <select  class="form-select" id="evento" aria-describedby="emailHelp" name="evento" onchange="location.href='srvCompra?eventoSelect=' + encodeURIComponent(this.value)">
				    <% if(hayEventos){ %>
				    	<% for(DtEvento dte : Eventos){ %>
				    		 <% if(dte != null){ %>
						 <option value="<%= dte.getNombre() %>"  <% if (selEvento != null && selEvento.equals(dte.getNombre())) { %>selected <% }%> > 
						 <%= dte.getNombre() %>
						 </option>
    					<% } %>
    				<% } %>
    				<% } %>
					</select>
				    <label for="nombre" class="altaUsuLetra  text-start">Edicion:</label>

			<div class="accordion" id="accordionExample">
<%
    if (Ediciones != null && !Ediciones.isEmpty()) {
    	boolean tengoEdicionesEnFecha= false;
        for (DtEdicion dtedi : Ediciones) {
        	if(true){
        	//if(LocalDate.now().isBefore(dtedi.getFechaFin())){
        		tengoEdicionesEnFecha= true;
	          	 boolean abierto = (ide == 0); // Solo el primero abierto
	            ide++;
%>			
    <div class="accordion-item">
        <h2 class="accordion-header" id="heading<%=ide%>">
            <button class="accordion-button <%= abierto ? "" : "collapsed" %>" type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#collapse<%=ide%>"
                    aria-expanded="<%= abierto %>"
                    aria-controls="collapse<%=ide%>">
                <%= dtedi.getNombre() %>
            </button>
        </h2>

        <div id="collapse<%=ide%>" class="accordion-collapse collapse <%= abierto ? "show" : "" %>"
             aria-labelledby="heading<%=ide%>" data-bs-parent="#accordionExample">
            <div class="accordion-body">
                <div class="row">
                    <div class="col-4">
                        <img src="<%= dtedi.getNombre() %>" class="imgorigin rounded float-start img-fluid bordes"
                             alt="Imagen no disponible" width="230" height="220">
                    </div>
                    <div class="col">
                        <h3 class="fuente1 resptext negrita"><%= dtedi.getNombre() %></h3>
                    </div>
                </div>

                <div class="row mt-4">
                    <p class="lh-1"><span class="negrita fuente1 ajustarTexto">Ciudad:</span> <span class="ajustarTexto"><%= dtedi.getCiudad() %></span></p>
                    <p class="lh-1"><span class="negrita fuente1 ajustarTexto">País:</span> <span class="ajustarTexto"><%= dtedi.getPais() %></span></p>
                    <p class="lh-1"><span class="negrita fuente1 ajustarTexto">Fecha de inicio:</span> <span class="ajustarTexto"><%= dtedi.getFechaInicio().toString() %></span></p>
                    <p class="lh-1"><span class="negrita fuente1 ajustarTexto">Fecha de fin:</span> <span class="ajustarTexto"><%= dtedi.getFechaFin().toString() %></span></p>
                </div>

                <label class="altaUsuLetra text-start">Tipo de registro:</label>
                <select class="form-select combo" id="tipoRegistro" name="tipoRegistro<%=ide%>">
                <%if (tipoRegistro.get(dtedi.getNombre())!=null){ for (DtTipoRegistro itemTipo : tipoRegistro.get(dtedi.getNombre()) ){ if(itemTipo!=null){ %> 
                    <option value="<%= itemTipo.getCosto() %>;<%= itemTipo.getCantCupos() %>;<%= itemTipo.getNombre() %>"><%= itemTipo.getNombre() %></option>
              	<% }}} %>
                </select>
				<label class="altaUsuLetra mt-2">Cupos:</label>
                <input type="text" class="form-control itemCombo" name="cupos<%=ide%>" value="<%= tipoRegistro.get(dtedi.getNombre()).getFirst().getCantCupos() %>"readonly>
                
                <label class="altaUsuLetra mt-2">Costo:</label>
                <input type="text" class="form-control itemCombo" name="costo<%=ide%>" value="<%= tipoRegistro.get(dtedi.getNombre()).getFirst().getCosto() %>" readonly>

                <button type="submit" name="aceptar" class="btn mt-4 btn-primary me-md-2 c2 btn-sm negrita fuente1"
                        value="<%= dtedi.getNombre() %>;<%=ide%>">Realizar compra</button>
            </div>
        </div>
    </div>
<%
        } // fin for
        	
    }
        if(!tengoEdicionesEnFecha){
        	%>
            <p>No hay eventos disponibles.</p>
        <%
        }
        }
    else {
%>
    <p>No hay eventos disponibles.</p>
<%
    }
%>
</div>
			

					 
				    
				 	<label for="contrabis" class="altaUsuLetra">Cupon:</label>
				    <input type="text" class="form-control" id="cupon" aria-describedby="emailHelp" name="cupon">   
				   
				    
				  </div>		
			
				  <div class="col-6 align-self-center">
						&nbsp
			      </div>        
     			</div>
			</form>
			</div>
     	  </div>
     </div>
<script type="text/javascript">
//Seleccionamos todos los combobox
const combos = document.querySelectorAll(".combo");

//Escuchamos todos los combobox
document.querySelectorAll(".combo").forEach(combo => {
  combo.addEventListener("change", function() {
    // buscamos el input con el mismo name que el combo
    const costoId = combo.name.replace("tipoRegistro", "costo");
    const inCosto = document.getElementsByName(costoId)[0]
    const cupoId = combo.name.replace("tipoRegistro", "cupos")
    const inCupo = document.getElementsByName(cupoId)[0];
    
	console.log(inCupo);
	const [costos, cupo] = combo.selectedOptions[0].value.split(';');
    if (inCosto) {
    	
      // actualizamos el valor del input con la opción seleccionada
      inCosto.value = costos;
      inCupo.value = cupo;
    }
  });
});

//Ejemplo de JavaScript inicial para deshabilitar el envío de formularios si hay campos no válidos
(function () {
  'use strict';

  // Obtener todos los formularios a los que queremos aplicar estilos de validación de Bootstrap personalizados
  var forms = document.querySelectorAll('.needs-validation');

  // formulario
  const form = document.getElementById('formUsuario');

  form.addEventListener('submit', (event) => {
    window.location.href = 'index.html';
  });

})();
	
	
	
</script>

<script src="../js/bootstrap.bundle.js"></script>

</body>
</html>