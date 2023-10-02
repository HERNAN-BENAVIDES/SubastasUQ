package co.edu.uniquindio.subastasuq.utils;

import co.edu.uniquindio.subastasuq.model.Producto;
import co.edu.uniquindio.subastasuq.model.Subasta;
import co.edu.uniquindio.subastasuq.model.TipoProducto;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;

public class UsuarioAnuncianteUtils {

    public static UsuarioAnunciante inicializarDatos() {
        UsuarioAnunciante usuarioAnunciante = new UsuarioAnunciante();

        Producto producto = new Producto("TV", TipoProducto.TECNOLOGIA, "12345", "Nuevo");
        usuarioAnunciante.getListProductos().add(producto);

        return usuarioAnunciante;
    }

}
