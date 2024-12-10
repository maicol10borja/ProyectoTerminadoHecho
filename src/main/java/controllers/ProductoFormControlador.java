package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Categoria;
import models.Productos;
import service.ProductoService;
import service.ProductoServiceJdbcImplement;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/productos/form")
public class ProductoFormControlador extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Presentamos el formulario
        //necesitamos la conexion
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImplement(conn);
        req.setAttribute("categorias", service.listarCategoria());
        getServletContext().getRequestDispatcher("/formularioProducto.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn= (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImplement(conn);
        String nombre = req.getParameter("nombre");
        Double precio;
        try{
            precio=Double.valueOf(req.getParameter("precio"));
        }catch (NumberFormatException e){
            precio=0.0;
        }
        Long idCategoria;
        try{
            idCategoria=Long.valueOf(req.getParameter("categoria"));
        }catch (NumberFormatException e){
            idCategoria=0L;
        }
        Productos productos= new Productos();
        productos.setNombre(nombre);
        Categoria categoria= new Categoria();
        categoria.setIdCategoria(idCategoria);
        productos.setCategoria(categoria);
        productos.setPrecio(precio);
        service.guarda(productos);
        //REdireccional a un listado para que no se ejecute el método doPost
        //nuevamente y se guarde los datos duplicados
        resp.sendRedirect(req.getContextPath()+"/productos");
    }
}
