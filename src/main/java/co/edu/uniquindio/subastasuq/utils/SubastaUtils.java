package co.edu.uniquindio.subastasuq.utils;

import co.edu.uniquindio.subastasuq.model.Producto;
import co.edu.uniquindio.subastasuq.model.Subasta;
import co.edu.uniquindio.subastasuq.model.TipoProducto;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;

public class SubastaUtils {

    public static Subasta inicializarDatos() {
        Subasta subasta = new Subasta();

        UsuarioAnunciante anunciante = new UsuarioAnunciante("Juan","Perez","12345",18,"juanPerez@gmail.com","12345");
        subasta.getListAnunciantes().add(anunciante);

        Producto producto = new Producto("TV", TipoProducto.TECNOLOGIA, "12345", true);
        anunciante.getListProductos().add(producto);

        return subasta;
    }

}
