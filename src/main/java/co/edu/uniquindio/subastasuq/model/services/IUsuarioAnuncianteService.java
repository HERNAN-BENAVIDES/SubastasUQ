package co.edu.uniquindio.subastasuq.model.services;

import co.edu.uniquindio.subastasuq.excepcions.ProductoException;
import co.edu.uniquindio.subastasuq.model.Producto;

public interface IUsuarioAnuncianteService {

    public boolean agregarProducto(Producto producto);

    public boolean verificarExistenciaProducto(Producto producto) throws ProductoException;

    public boolean actualizarProducto(Producto producto, Producto productoActualizado) throws ProductoException;


}
