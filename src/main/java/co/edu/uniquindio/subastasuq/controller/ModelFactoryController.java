package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.controller.services.IModelFactoryController;
import co.edu.uniquindio.subastasuq.excepcions.ProductoException;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.mapping.mappers.UsuarioAnuncianteMapper;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;
import co.edu.uniquindio.subastasuq.utils.Persistencia;
import co.edu.uniquindio.subastasuq.utils.UsuarioAnuncianteUtils;

import java.io.IOException;
import java.util.List;

public class ModelFactoryController implements IModelFactoryController {

    private static ModelFactoryController instance;
    UsuarioAnunciante usuarioAnunciante;

    public ModelFactoryController() {
        //cargarDatosBase();
        //salvarDatosPrueba();
        cargarDatosArchivo();
    }

    private void cargarDatosArchivo() {
        usuarioAnunciante = new UsuarioAnunciante();

        try {
            Persistencia.cargarDatosArchivo(usuarioAnunciante);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void salvarDatosPrueba() {
        try {
            Persistencia.guardarProductos(usuarioAnunciante.getListProductos());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarDatosBase() {
        usuarioAnunciante = UsuarioAnuncianteUtils.inicializarDatos();
    }

    @Override
    public boolean agregarProducto(ProductoDto productoDto) throws ProductoException {
        if(!usuarioAnunciante.verificarExistenciaProducto(UsuarioAnuncianteMapper.productoDtoToProducto(productoDto))){
            usuarioAnunciante.getListProductos().add(UsuarioAnuncianteMapper.productoDtoToProducto(productoDto));
            salvarDatosPrueba();
            return true;
        }
        return false;
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public List<ProductoDto> obtenerProductos() {
        return UsuarioAnuncianteMapper.getListProductos(usuarioAnunciante.getListProductos());
    }

    public boolean eliminarProducto(ProductoDto productoSeleccionado) throws ProductoException {
        return usuarioAnunciante.eliminarProducto(UsuarioAnuncianteMapper.productoDtoToProducto(productoSeleccionado));
    }

    public boolean actualizarProducto(ProductoDto productoSeleccionado, ProductoDto productoNuevo) throws ProductoException {
        return  usuarioAnunciante.actualizarProducto(UsuarioAnuncianteMapper.productoDtoToProducto(productoSeleccionado),
                UsuarioAnuncianteMapper.productoDtoToProducto(productoNuevo));
    }

    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }
}
