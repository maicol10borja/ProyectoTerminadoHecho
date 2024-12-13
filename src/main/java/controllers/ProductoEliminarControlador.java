package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductoService;
import service.ProductoServiceJdbcImplement;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/productos/eliminar")
public class  ProductoEliminarControlador extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener la conexión
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImplement(conn);

        // Obtener el id del producto que se quiere eliminar
        long idProducto;
        try {
            idProducto = Long.parseLong(req.getParameter("idProducto"));
        } catch (NumberFormatException e) {
            idProducto = 0L;
        }

        // Si el id es válido, proceder a eliminar
        if (idProducto > 0) {
            // Llamamos al metodo eliminar del servicio
            service.eliminar(idProducto);
        }

        // Redirigir al listado de productos después de la eliminación
        resp.sendRedirect(req.getContextPath() + "/productos");
    }
}
