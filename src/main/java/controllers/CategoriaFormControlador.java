package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Categoria;
import service.CategoriaService;
import service.CategoriaServiceJdbcImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/categoria/form")
public class CategoriaFormControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener la conexión y el servicio de categoría
        Connection conn = (Connection) req.getAttribute("conn");
        CategoriaService service = new CategoriaServiceJdbcImplement(conn);

        // Obtener el listado de categorías para pasarlo al JSP si es necesario
        req.setAttribute("categorias", service.listarCategoria());

        long id = 0L;
        try {
            // Obtener el id de la categoría si está en los parámetros
            id = Long.parseLong(req.getParameter("idCategoria"));
        } catch (NumberFormatException e) {
            // Si no hay id válido, se mantiene en 0
        }

        // Crear un objeto vacío de tipo Categoria
        Categoria categoria = new Categoria();

        if (id > 0) {
            // Si el id es válido, se busca la categoría por id
            Optional<Categoria> o = service.porIdCategoria(id);
            if (o.isPresent()) {
                categoria = o.get();  // Se asigna la categoría encontrada
            }
        }

        // Pasar la categoría al JSP para mostrarla
        req.setAttribute("categoria", categoria);
        getServletContext().getRequestDispatcher("/formularioCategoria.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener la conexión y el servicio
        Connection conn = (Connection) req.getAttribute("conn");
        CategoriaService service = new CategoriaServiceJdbcImplement(conn);

        // Obtener el nombre de la categoría desde el formulario
        String nombre = req.getParameter("nombre");

        // El estado siempre debe ser 1 (activo), independientemente de lo que venga del formulario
        int estado = 1;  // Se sobreescribe a 1 (activo)

        // Obtener el id de la categoría, si se está editando
        long idCategoria;
        try {
            idCategoria = Long.parseLong(req.getParameter("idCategoria"));
        } catch (NumberFormatException e) {
            idCategoria = 0L;  // Valor por defecto si no se pasa id
        }

        // Crear un objeto Categoria y asignar los valores obtenidos
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(idCategoria);
        categoria.setNombre(nombre);
        categoria.setEstado(estado);  // Se asegura que el estado sea 1

        // Guardar o actualizar la categoría en la base de datos
        service.guarda(categoria);

        // Redirigir a la lista de categorías después de guardar
        resp.sendRedirect(req.getContextPath() + "/categoria");
    }
}
