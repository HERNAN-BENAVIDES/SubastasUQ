package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.excepcions.AutenticacionException;
import co.edu.uniquindio.subastasuq.excepcions.ProductoException;
import co.edu.uniquindio.subastasuq.excepcions.UsuarioException;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.mapping.mappers.ProductoMapper;
import co.edu.uniquindio.subastasuq.model.Subasta;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;
import co.edu.uniquindio.subastasuq.utils.Persistencia;
import co.edu.uniquindio.subastasuq.utils.SubastaUtils;

import java.io.IOException;
import java.util.List;

public class ModelFactoryController {

    private static ModelFactoryController instance;
    private Subasta subasta;
    private UsuarioAnunciante usuarioAnunciante;

    public ModelFactoryController() {
        cargarDatosBase();
        salvarDatosPrueba();
//        cargarDatosPrueba();
    }

    public void setUsuarioAnunciante(UsuarioAnunciante usuarioAnunciante){
        this.usuarioAnunciante = usuarioAnunciante;
    }

    private void cargarDatosPrueba() {
        subasta = new Subasta();
        try {
            Persistencia.cargarDatosArchivo(subasta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void salvarDatosPrueba() {
        try {
            Persistencia.guardarAnunciantes(subasta.getListAnunciantes());
            Persistencia.guardarCompradores(subasta.getListCompradores());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarDatosBase() {
        subasta = SubastaUtils.inicializarDatos();
      //  usuarioAnunciante = UsuarioAnuncianteUtils.inicializarDatos();
    }



    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public boolean iniciarSesion(String user, String password, String tipo) throws IOException, AutenticacionException {
        return Persistencia.iniciarSesion(user, password, tipo);
    }

    public UsuarioAnunciante obtenerUsuarioAnunciante(String user) throws UsuarioException {
        return subasta.obtenerUsuarioAnunciante(user);
    }

    public boolean agregarProducto(ProductoDto productoDto) {
        return usuarioAnunciante.agregarProducto(ProductoMapper.productoDtoToProducto(productoDto));
    }

    public List<ProductoDto> obtenerProductos() {
        return ProductoMapper.getListProductos(usuarioAnunciante.getListProductos());
    }

    public boolean eliminarProducto(ProductoDto productoSeleccionado) throws ProductoException {
        return  usuarioAnunciante.eliminarProducto(ProductoMapper.productoDtoToProducto(productoSeleccionado));
    }

    public boolean actualizarProducto(ProductoDto productoSeleccionado, ProductoDto productoNuevo) throws ProductoException {
        return usuarioAnunciante.actualizarProducto(ProductoMapper.productoDtoToProducto(productoSeleccionado),
                ProductoMapper.productoDtoToProducto(productoNuevo));
    }

    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }
}
