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
        //1
//        cargarDatosBase();

        //2
//        salvarDatosPrueba();
        cargarDatosPrueba();

        //3
//        guardarResourceBinario();
//        cargarResourceBinario();

        //4
//        guardarResourceXML();
//        cargarResourceXML();

        if(subasta == null){
            cargarDatosBase();
            guardarResourceXML();
        }


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
            Persistencia.guardarAnunciantes2(subasta.getListAnunciantes());
            Persistencia.guardarCompradores(subasta.getListCompradores());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarDatosBase() {
        subasta = SubastaUtils.inicializarDatos();
    }

    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public boolean iniciarSesion(String user, String password, String tipo) throws IOException, AutenticacionException {
        if(Persistencia.iniciarSesion(user, password, tipo)){
            Persistencia.guardarRegistroLog("Inicio de sesión",1,"Inicio de sesión: " + "user: " + user);
            return true;
        }
        return false;
    }

    public UsuarioAnunciante obtenerUsuarioAnunciante(String user) throws UsuarioException {
        return subasta.obtenerUsuarioAnunciante(user);
    }

    public boolean agregarProducto(ProductoDto productoDto) throws ProductoException {
        if(usuarioAnunciante.agregarProducto(ProductoMapper.productoDtoToProducto(productoDto))){
            Persistencia.guardarRegistroLog("Usuario: " + usuarioAnunciante.getNombre() + " " + usuarioAnunciante.getApellido(),1,"Registrar producto");
            salvarDatosPrueba();
            return true;
        }
        return false;
    }

    public List<ProductoDto> obtenerProductos() {
        return ProductoMapper.getListProductos(usuarioAnunciante.getListProductos());
    }

    public boolean eliminarProducto(ProductoDto productoSeleccionado) throws ProductoException {
        if(usuarioAnunciante.eliminarProducto(ProductoMapper.productoDtoToProducto(productoSeleccionado))){
            registrarAccionesSistema("Usuario: " + usuarioAnunciante.getNombre() + " " + usuarioAnunciante.getApellido(),1,"Eliminar producto");
            salvarDatosPrueba();
            return true;
        }
        return false;
    }

    public boolean actualizarProducto(ProductoDto productoSeleccionado, ProductoDto productoNuevo) throws ProductoException {
        if(usuarioAnunciante.actualizarProducto(ProductoMapper.productoDtoToProducto(productoSeleccionado),
                ProductoMapper.productoDtoToProducto(productoNuevo))){
            registrarAccionesSistema("Usuario: " + usuarioAnunciante.getNombre() + " " + usuarioAnunciante.getApellido(),1,"Actualizar producto");
            salvarDatosPrueba();
            return  true;
        }

        return false;
    }

    public void registrarAccionesSistema(String mensaje, int nivel, String accion){
        Persistencia.guardarRegistroLog(mensaje,nivel,accion);
    }


    /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------BINARIO--------------------------------------------------------------
     */
    private void guardarResourceBinario() {
        Persistencia.guardarRecursoBancoBinario(subasta);
    }
    private void cargarResourceBinario() {
        subasta = Persistencia.cargarRecursoBancoBinario();
    }

        /*
-----------------------------------------------------------------------------------------------------------
--------------------------------------XML--------------------------------------------------------------
 */

    private void cargarResourceXML() {
        subasta = Persistencia.cargarRecursoBancoXML();
    }

    private void guardarResourceXML() {
        Persistencia.guardarRecursoBancoXML(subasta);
    }


}
