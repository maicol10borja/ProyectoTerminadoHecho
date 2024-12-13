<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, models.*" %>
<%
  List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
  Categoria categoria = (Categoria) request.getAttribute("categoria");
%>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Ingreso de Categoria</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      margin-top: 20px;
    }
    .form-container {
      max-width: 600px;
      margin: 0 auto;
    }
  </style>
</head>

<body>

<div class="container form-container">
  <h1 class="text-center">Formulario de Categoria</h1>
  <form action="<%= request.getContextPath() %>/categoria/form" method="post">
    <div class="form-group">
      <label for="nombre">Ingrese el nombre de la Categoria:</label>
      <input type="hidden" name="idCategoria" value="<%= categoria != null && categoria.getIdCategoria() != null ? categoria.getIdCategoria() : "" %>">
      <input type="text" id="nombre" name="nombre" class="form-control" value="<%= categoria != null && categoria.getNombre() != null ? categoria.getNombre() : "" %>" required>
    </div>

    <!-- Eliminar el campo de estado, ya que siempre se asignará a 1 (activo) en el servidor -->
    <input type="hidden" name="estado" value="1">  <!-- Estado fijo como activo (1) -->

    <div class="form-group text-center">
      <input type="submit" value="ENVIAR" class="btn btn-primary">
    </div>
  </form>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
