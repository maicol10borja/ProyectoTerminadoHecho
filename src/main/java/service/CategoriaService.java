package service;

import models.Categoria;
import models.Productos;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> listar();
    //implementamos el metodo para añadir
    Optional<Categoria> agregarPorId(Long idCategoria);
    //Implmentamos el metodo guardar , eliminar y listar de categoria
    void guarda(Categoria categoria);
    //metodo eliminar
    void eliminar(Long id);

    //Listarmos al Categoria
    List<Categoria> listarCategoria();
    Optional<Categoria> porIdCategoria(Long idCategoria);
}
