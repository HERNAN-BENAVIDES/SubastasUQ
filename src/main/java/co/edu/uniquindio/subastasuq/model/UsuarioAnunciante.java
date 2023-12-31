package co.edu.uniquindio.subastasuq.model;

import co.edu.uniquindio.subastasuq.excepcions.ProductoException;
import co.edu.uniquindio.subastasuq.model.services.IUsuarioAnuncianteService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsuarioAnunciante extends Usuario implements IUsuarioAnuncianteService, Serializable {

    private static final long serialVersionUID = 1L;
    private List<Anuncio> listAnuncios = new ArrayList<Anuncio>();
    private List<Producto> listProductos = new ArrayList<Producto>();

    public UsuarioAnunciante() {

    }

    public UsuarioAnunciante(String nombre, String apellido, String cedula, Integer edad, String username, String password) {
        super(nombre, apellido, cedula, edad, username, password);
    }

    public List<Anuncio> getListAnuncios() {
        return listAnuncios;
    }

    public void setListAnuncios(List<Anuncio> listAnuncios) {
        this.listAnuncios = listAnuncios;
    }

    public List<Producto> getListProductos() {
        return listProductos;
    }

    public void setListProductos(List<Producto> listProductos) {
        this.listProductos = listProductos;
    }



    public boolean agregarProducto(Producto producto){
        return getListProductos().add(producto);
    }

    public boolean verificarExistenciaProducto(Producto producto) throws ProductoException {
        if (getListProductos().contains(producto)) {
            throw new ProductoException("El producto ya se encuentra registrado");
        }
        return false;
    }


    public boolean actualizarProducto(Producto producto, Producto productoActualizado) throws ProductoException {

        if(producto == null){
            throw new ProductoException("El Producto a actualizar no existe");
        }else{
            int i = getListProductos().indexOf(producto);

            if (i != -1) {
                getListProductos().set(i, productoActualizado); // Reemplaza el producto antiguo con el producto actualizado
                return true; // Retorna true para indicar que se actualizó exitosamente
            }
        }
        return false;
    }

    public boolean eliminarProducto (Producto producto) throws ProductoException {
        if(producto != null){
            listProductos.remove(producto);
            return true;
        }else{
            throw new ProductoException("El Producto seleccionado no existe");
        }

    }
}
