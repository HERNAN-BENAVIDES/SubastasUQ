package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.excepcions.AnuncioException;
import co.edu.uniquindio.subastasuq.excepcions.AutenticacionException;
import co.edu.uniquindio.subastasuq.excepcions.ProductoException;
import co.edu.uniquindio.subastasuq.excepcions.UsuarioException;
import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.mapping.mappers.AnuncioMapper;
import co.edu.uniquindio.subastasuq.mapping.mappers.ProductoMapper;
import co.edu.uniquindio.subastasuq.model.Producto;
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
        cargarDatosBase();

        //2
//        salvarDatosPrueba();
//        cargarDatosPrueba();

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

    public UsuarioAnunciante obtenerUsuarioAnunciante(String user) throws UsuarioException {
        return subasta.obtenerUsuarioAnunciante(user);
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




    /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------GET INSTANCE--------------------------------------------------------------
 */
    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

        /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------INICIO SESION--------------------------------------------------------------
     */

    public boolean iniciarSesion(String user, String password, String tipo) throws IOException, AutenticacionException {
        if(Persistencia.iniciarSesion(user, password, tipo)){
            Persistencia.guardarRegistroLog("Inicio de sesión",1,"Inicio de sesión: " + "user: " + user);
            return true;
        }
        return false;
    }

    /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------PRODUCTOS--------------------------------------------------------------
     */

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

        /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------ANUNCIOS--------------------------------------------------------------
     */

    public List<AnuncioDto> obtenerAnuncios() {
        return AnuncioMapper.getListAnunciosDto(usuarioAnunciante.getListAnuncios());
    }

    public String[] obtenerProductosNombres() {
        List<Producto> listaProductos = usuarioAnunciante.getListProductos();
        String[] nombres = new String[listaProductos.size()+1];
        nombres[0] = "Selecciona";
        for (int i = 0; i < listaProductos.size(); i++) {
            nombres[i+1] = listaProductos.get(i).getNombre();
        }

        return nombres;
    }

    public ProductoDto obtenerProductoSeleccionado(String nombreProducto) {
        List<Producto> listaProductos = usuarioAnunciante.getListProductos();

        for (Producto producto: listaProductos) {
            if (producto.getNombre().equals(nombreProducto)){
                return ProductoMapper.productoToProductoDto(producto);
            }
        }
        return  null;
    }

    public boolean agregarAnuncio(AnuncioDto anuncioDto) throws AnuncioException {
        return usuarioAnunciante.agregarAnuncio(AnuncioMapper.anuncioDtoToAnuncio(anuncioDto));
    }

    public boolean eliminarAnuncio(AnuncioDto elementoSeleccionado) throws AnuncioException {
        return usuarioAnunciante.eliminarAnuncio(AnuncioMapper.anuncioDtoToAnuncio(elementoSeleccionado));
    }

    public boolean actualizarAnuncio(AnuncioDto elementoSeleccionado, AnuncioDto anuncioDto) throws AnuncioException {
        return usuarioAnunciante.actualizarAnuncio(AnuncioMapper.anuncioDtoToAnuncio(elementoSeleccionado),
                AnuncioMapper.anuncioDtoToAnuncio(anuncioDto));
    }

    /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------LOG--------------------------------------------------------------
     */
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
