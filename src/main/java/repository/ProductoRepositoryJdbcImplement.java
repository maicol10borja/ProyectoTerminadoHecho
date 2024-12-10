package repository;

import models.Productos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryJdbcImplement implements Repository<Productos>{
    /*Necesitamos la cpnexión a la bbdd, la conexión tiene que
    * pasar por la clase Service y a su vez pasa al servlet esa conexión
    * la obtiene po el método request medianyte los atributos
    * con el getAtribute y se lo pasa nuevamente al Service
    * y para finalmente pasarlo al repository*/
    private Connection conn;

    public ProductoRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    public ProductoRepositoryJdbcImplement() {
        super();
    }

    @Override
    public List<Productos> listar() throws SQLException {
        List<Productos> productos = new ArrayList<Productos>();
        try(Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("select p.*, c.nombre as " +
                    " categoria from " +
                    " producto as p inner join categoria as c " +
                    " ON (p.idcategoria=c.idcategoria)")){
            while(rs.next()){
                Productos p = getProductos(rs);
                productos.add(p);
            }
        }
        return productos;
    }



    @Override
    public Productos porId(Long idProducto) throws SQLException {
        return null;
    }

    @Override
    public void guardar(Productos productos) throws SQLException {

    }

    @Override
    public void eliminar(Long idProducto) throws SQLException {

    }
    private static Productos getProductos(ResultSet rs) throws SQLException {
        Productos p=new Productos();
        p.setIdProducto(rs.getLong("idproducto"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getDouble("precio"));
        p.setCategoria(rs.getString("categoria"));
        return p;
    }
}
