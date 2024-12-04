package repository;

import java.sql.SQLException;
import java.util.List;

/*
* <T> Es un parámetro genérico que permite
* que la interfaz sea utilizada con cualquier
* tipo de objeto(entidad) que desees manejar.
* Esto hace que la interfaz  sea flexible y
* reutilizable para diferentes tipos
* de datos*/
public interface Repository <T>{
    /*El método listar retorna una lista de
    objetos de tipo genérico T. Se usa para obtener
    todoslos registros de una entidad desde la
    bbdd
    * */
    List<T> listar() throws SQLException;
    /*
    * El método porId  recibe un identificador único
    * y retorna un objeto de tipo T
    * correspondiente al identificador
    * Se usa para buscar un registro especifico
    * por su id*/
    T porId(Long idProducto)throws SQLException;
    /*
    * El método guardar recibe un objeto de tipo T
    * y lo guarda en la bbdd, este método puede
    * ser utilizado para crear o actualizar un
    * registro, dependiendo si el objeto
    * ya existe en la bbdd*/
    void guardar(T t)throws SQLException;
    /*El método eliminar recibe un identificador
    * y elimina el registro correspondiente*/
    void eliminar(Long idProducto)throws SQLException;
}
