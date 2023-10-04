package co.edu.uniquindio.subastasuq.utils;

import co.edu.uniquindio.subastasuq.model.Producto;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;

public class UsuarioAnuncianteUtils {

    public static UsuarioAnunciante inicializarDatos() {
        UsuarioAnunciante usuarioAnunciante = new UsuarioAnunciante();

        Producto producto = new Producto("TV", "Tecnologia", "12345", "Nuevo");
        Producto producto1 = new Producto("Moto", "Vehiculo", "12345", "Nuevo");
        Producto producto2 = new Producto("Bicicleta", "Deporte", "12345", "Nuevo");
        Producto producto3 = new Producto("Casa", "Bienraiz", "12345", "Nuevo");

        usuarioAnunciante.getListProductos().add(producto);
        usuarioAnunciante.getListProductos().add(producto1);
        usuarioAnunciante.getListProductos().add(producto2);
        usuarioAnunciante.getListProductos().add(producto3);

        return usuarioAnunciante;
    }
}
