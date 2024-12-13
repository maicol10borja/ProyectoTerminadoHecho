package service;

import models.Categoria;
import repositories.CategoriaRepositoryJdbcImplement;
import repositories.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategoriaServiceJdbcImplement implements CategoriaService {
    private Repository<Categoria> repositoryJdbc;

    public CategoriaServiceJdbcImplement(Connection connection) {
        this.repositoryJdbc = new CategoriaRepositoryJdbcImplement(connection);
    }

    @Override
    public List<Categoria> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Categoria> agregarPorId(Long idCategoria) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(idCategoria));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void guarda(Categoria categoria) {
        try {
            repositoryJdbc.guardar(categoria);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            repositoryJdbc.eliminar(id);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    // Método corregido para listar categorías
    @Override
    public List<Categoria> listarCategoria() {
        try {
            return repositoryJdbc.listar();  // Se hace uso del método listar del repositorio
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    // Método corregido para obtener una categoría por ID
    @Override
    public Optional<Categoria> porIdCategoria(Long idCategoria) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(idCategoria));  // Se obtiene de repositorio
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

}
