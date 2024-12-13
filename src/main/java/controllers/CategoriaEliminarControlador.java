package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CategoriaService;
import service.CategoriaServiceJdbcImplement;
import service.ProductoService;
import service.ProductoServiceJdbcImplement;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/categoria/eliminar")
public class  CategoriaEliminarControlador extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener la conexión
        Connection conn = (Connection) req.getAttribute("conn");
        CategoriaService service = new CategoriaServiceJdbcImplement(conn);

        // Obtener el id del producto que se quiere eliminar
        long idCategoria;
        try {
            idCategoria = Long.parseLong(req.getParameter("idCategoria"));
        } catch (NumberFormatException e) {
            idCategoria = 0L;
        }

        // Si el id es válido, proceder a eliminar
        if (idCategoria > 0) {
            // Llamamos al metodo eliminar del servicio
            service.eliminar(idCategoria);
        }

        // Redirigir al listado de productos después de la eliminación
        resp.sendRedirect(req.getContextPath() + "/categoria");
    }
}
