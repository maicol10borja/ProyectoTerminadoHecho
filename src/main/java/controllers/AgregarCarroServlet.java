package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Carro;
import models.ItemCarro;
import models.Productos;
import service.ProductoService;
import service.ProductoServiceJdbcImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/agregar-carro")
public class AgregarCarroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idProducto = Long.parseLong(req.getParameter("idProducto"));
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImplement(conn);

        // Obtener el producto
        Optional<Productos> producto = service.agregarPorId(idProducto);

        if (producto.isPresent()) {
            // Crear el item del carrito
            ItemCarro item = new ItemCarro(1, producto.get());

            // Obtener la sesión y el carrito
            HttpSession session = req.getSession();
            Carro carro;
            if (session.getAttribute("carro") == null) {
                carro = new Carro();
                session.setAttribute("carro", carro);
            } else {
                carro = (Carro) session.getAttribute("carro");
            }

            // Agregar el item al carrito
            carro.addItemCarro(item);

            // Reducir el stock del producto
            Productos prod = producto.get();
            if (prod.getStock() > 0) {
                int nuevoStock = prod.getStock() - 1;
                service.actualizarStock(idProducto, nuevoStock);
            }
        }

        // Redirigir al carrito
        resp.sendRedirect(req.getContextPath() + "/ver-carro");
    }

}
