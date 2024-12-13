package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Categoria;
import models.Productos;
import service.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
@WebServlet("/categoria")
public class CategoriaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*ProductoService servicios = new ProductoServiceImplement();
        List<Productos> productos = servicios.listar();*/

        //Creamos la conexión u obtenemos la conexión
        Connection conn = (Connection)req.getAttribute("conn");
        //Creamos el nuevo objeto
        CategoriaService service = new CategoriaServiceJdbcImplement(conn);
        List<Categoria> categoria=service.listar();


        //Seteamos lo atributos de producto y el username
        req.setAttribute("categoria", categoria);
        // redireccionamos a la vista indicada listar.jsp
        getServletContext().getRequestDispatcher("/listar-categorias.jsp").forward(req,resp);
    }
}
