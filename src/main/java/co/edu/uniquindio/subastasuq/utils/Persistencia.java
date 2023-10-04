package co.edu.uniquindio.subastasuq.utils;

import co.edu.uniquindio.subastasuq.model.Producto;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    public static final String RUTA_ARCHIVO_PRODUCTOS = "src/main/resources/persistencia/archivoProductos.txt";
    public static void guardarProductos(List<Producto> listProductos) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (Producto producto : listProductos) {
            contenido.append(producto.getNombre()).append("@@").append(producto.getTipoProducto()).append("@@").append(producto.getCodigo()).append("@@").append(producto.getEstado()).append("\n");
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PRODUCTOS, contenido.toString(),false);
    }

    public static void cargarDatosArchivo(UsuarioAnunciante usuarioAnunciante) throws IOException {
        List<Producto> listProductos = cargarProductos();
        if (!listProductos.isEmpty()){
            usuarioAnunciante.getListProductos().addAll(listProductos);
        }
    }



    public static List<Producto> cargarProductos() throws IOException {
        List<Producto> productos = new ArrayList<Producto>();
        List<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_PRODUCTOS);

        for (String linea : contenido) {
            String[] partes = linea.split("@@");

            if (partes.length == 4) {
                String nombre = partes[0];
                String tipoProducto = partes[1];
                String codigo = partes[2];
                String estado = partes[3];


                Producto producto = new Producto(nombre, tipoProducto, codigo, estado);
                productos.add(producto);
            }
        }

        return productos;
    }

}
