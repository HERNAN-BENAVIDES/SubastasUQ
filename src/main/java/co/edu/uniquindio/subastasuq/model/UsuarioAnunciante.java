package co.edu.uniquindio.subastasuq.model;

import co.edu.uniquindio.subastasuq.excepcions.AnuncioException;
import co.edu.uniquindio.subastasuq.excepcions.ProductoException;
import co.edu.uniquindio.subastasuq.model.services.IUsuarioAnuncianteService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsuarioAnunciante extends Usuario implements IUsuarioAnuncianteService, Serializable {

    private static final long serialVersionUID = 1L;
    private List<Anuncio> listAnuncios;
    private List<Producto> listProductos;

    public UsuarioAnunciante() {

    }

    public UsuarioAnunciante(String nombre, String apellido, String cedula, Integer edad, String username, String password) {
        super(nombre, apellido, cedula, edad, username, password);
        this.listAnuncios = new ArrayList<Anuncio>();
        this.listProductos = new ArrayList<Producto>();
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



    public boolean agregarProducto(Producto producto) throws ProductoException {
        if(!verificarExistenciaProducto(producto)){
            getListProductos().add(producto);
            return true;
        }
        return false;
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

    public boolean agregarAnuncio(Anuncio anuncio) throws AnuncioException {
        if(!verificarExistenciaAnuncio(anuncio)){
            getListAnuncios().add(anuncio);
            return true;
        }
        return false;
    }

    private boolean verificarExistenciaAnuncio(Anuncio anuncio) throws AnuncioException {
        if (getListAnuncios().contains(anuncio)) {
            throw new AnuncioException("El producto ya se encuentra registrado");
        }
        return false;
    }

    public boolean eliminarAnuncio(Anuncio anuncio) throws AnuncioException {
        if(anuncio != null){
            listAnuncios.remove(anuncio);
            return true;
        }else{
            throw new AnuncioException("El Producto seleccionado no existe");
        }
    }

    public boolean actualizarAnuncio(Anuncio elementoSeleccionado, Anuncio anuncioDto) throws AnuncioException {
        if(elementoSeleccionado == null){
            throw new AnuncioException("El anuncio a actualizar no existe");
        }else{
            int i = getListAnuncios().indexOf(elementoSeleccionado);

            if (i != -1) {
                getListAnuncios().set(i, anuncioDto); // Reemplaza el producto antiguo con el producto actualizado
                return true; // Retorna true para indicar que se actualizó exitosamente
            }
        }
        return false;
    }

    public List<Anuncio> getListAnunciosActivos() {
        List<Anuncio> anunciosActivos = new ArrayList<>();
        for (Anuncio anuncio: listAnuncios) {
            if(anuncio.getIsActivo()){
                anunciosActivos.add(anuncio);
            }
        }
        return anunciosActivos;
    }

    public List<Anuncio> getListAnunciosInactivos() {
        List<Anuncio> anunciosInactivos = new ArrayList<>();
        for (Anuncio anuncio: listAnuncios) {
            if(!anuncio.getIsActivo()){
                anunciosInactivos.add(anuncio);
            }
        }
        return anunciosInactivos;
    }
}
