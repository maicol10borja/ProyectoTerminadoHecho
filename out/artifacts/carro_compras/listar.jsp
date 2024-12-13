<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,models.*" %>
<%
    List<Productos> productos = (List<Productos>) request.getAttribute("productos");
    Optional<String> username = (Optional<String>) request.getAttribute("username");
%>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Productos</title>
    <!-- Vincula Bootstrap desde un CDN -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <!-- Estilos adicionales si son necesarios -->
    <style>
        body {
            margin-top: 20px;
        }
        table {
            margin-top: 20px;
        }
    </style>
</head>
<body>

<div class="container">
    <h1 class="text-center">Listado de Productos</h1>


    <% if(username.isPresent()) { %>
    <div class="alert alert-info">
        Hola <%= username.get() %>, bienvenido a la aplicación!
    </div>
    <div><p><a href="${pageContext.request.contextPath}/productos/form" class="btn btn-primary">Ingrese el producto</a></p></div>
    <% } %>

    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
        <tr>
            <th>ID PRODUCTO</th>
            <th>NOMBRE PRODUCTO</th>
            <th>CATEGORIA</th>
            <% if(username.isPresent()) { %>
            <th>STOCK</th>
            <th>PRECIO</th>
            <th>OPCIONES</th>
            <% } %>
        </tr>
        </thead>
        <tbody>
        <%
            for (Productos p: productos) {
        %>
        <tr>
            <td><%= p.getIdProducto() %></td>
            <td><%= p.getNombre() %></td>
            <td><%= p.getCategoria().getNombre() %></td>
            <% if(username.isPresent()) { %>
            <td><%= p.getStock() %></td>
            <td><%= p.getPrecio() %></td>
            <td>
                <a href="<%= request.getContextPath() %>/agregar-carro?idProducto=<%= p.getIdProducto() %>" class="btn btn-success btn-sm">Agregar</a>
                <a href="<%= request.getContextPath() %>/productos/form?idProducto=<%= p.getIdProducto() %>" class="btn btn-warning btn-sm">Editar</a>
                <a href="<%= request.getContextPath() %>/productos/eliminar?idProducto=<%= p.getIdProducto() %>" class="btn btn-danger btn-sm" onclick="return confirm('¿Estás seguro de eliminar este producto?');">Eliminar</a>
            </td>
            <% } %>
        </tr>
        <% } %>
        </tbody>
    </table>
    <div><p><a href="${pageContext.request.contextPath}/index.html" class="btn btn-warning">Volver</a></p></div>


</div>

<!-- Vincula los scripts de Bootstrap para funcionalidades como los botones (opcional) -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</body>
</html>
