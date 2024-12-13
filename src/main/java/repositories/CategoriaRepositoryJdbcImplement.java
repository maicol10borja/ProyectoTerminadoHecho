package repositories;

import models.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositoryJdbcImplement implements Repository<Categoria> {
    private Connection conn;

    public CategoriaRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categoria")) {
            while (rs.next()) {
                Categoria c = getCategoria(rs);
                categorias.add(c);
            }
        }
        return categorias;
    }

    @Override
    public Categoria porId(Long idCategoria) throws SQLException {
        Categoria categoria = null;
        try(PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM categoria WHERE idcategoria = ?")) {
            stmt.setLong(1, idCategoria);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoria = getCategoria(rs);
                }
            }
        }
        return categoria;
    }

    @Override
    public void guardar(Categoria categoria) throws SQLException {
        String sql;
        if (categoria.getIdCategoria() != null && categoria.getIdCategoria() > 0) {
            // Se actualiza una categoría existente
            sql = "UPDATE categoria SET nombre = ?, estado = ? WHERE idcategoria = ?";
        } else {
            // Se inserta una nueva categoría
            sql = "INSERT INTO categoria (nombre, estado) VALUES (?, ?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (categoria.getIdCategoria() != null && categoria.getIdCategoria() > 0) {
                stmt.setLong(3, categoria.getIdCategoria());  // En el caso de actualización
            }
            stmt.setString(1, categoria.getNombre());
            stmt.setInt(2, categoria.getEstado());  // El estado puede ser 1 (activo) o 0 (inactivo)
            stmt.executeUpdate();
        }
    }
//--
    @Override
    public void eliminar(Long idCategoria) throws SQLException {
        // Primero, obtener el estado actual de la categoría
        String sqlSelect = "SELECT estado FROM categoria WHERE idcategoria = ?";
        int estadoActual = -1;

        try (PreparedStatement stmt = conn.prepareStatement(sqlSelect)) {
            stmt.setLong(1, idCategoria);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    estadoActual = rs.getInt("estado");
                }
            }
        }

        // Si se encontró un estado válido, cambiarlo a su opuesto (de 0 a 1 o de 1 a 0)
        if (estadoActual != -1) {
            int nuevoEstado = (estadoActual == 0) ? 1 : 0;
            String sqlUpdate = "UPDATE categoria SET estado = ? WHERE idcategoria = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {
                stmt.setInt(1, nuevoEstado);
                stmt.setLong(2, idCategoria);
                stmt.executeUpdate();
            }
        } else {
            // Manejo de error si no se encuentra la categoría
            throw new SQLException("No se encontró la categoría con ID: " + idCategoria);
        }
    }

//--
    @Override
    public void actualizarStock(Long idProducto, int nuevoStock) throws SQLException {

    }

    private static Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setIdCategoria(rs.getLong("idcategoria"));
        c.setNombre(rs.getString("nombre"));
        c.setEstado(rs.getInt("estado"));
        return c;
    }
}
