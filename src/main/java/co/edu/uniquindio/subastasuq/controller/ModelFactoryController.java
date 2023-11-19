package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.excepcions.*;
import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.mapping.dto.PujaDto;
import co.edu.uniquindio.subastasuq.mapping.dto.UsuarioCompradorDto;
import co.edu.uniquindio.subastasuq.mapping.mappers.AnuncioMapper;
import co.edu.uniquindio.subastasuq.mapping.mappers.CompradorMapper;
import co.edu.uniquindio.subastasuq.mapping.mappers.ProductoMapper;
import co.edu.uniquindio.subastasuq.mapping.mappers.PujaMapper;
import co.edu.uniquindio.subastasuq.model.*;
import co.edu.uniquindio.subastasuq.utils.Persistencia;
import co.edu.uniquindio.subastasuq.utils.SubastaUtils;

import java.io.IOException;
import java.util.List;

public class ModelFactoryController {

    private Subasta subasta;
    private UsuarioAnunciante anunciante;
    private UsuarioComprador comprador;

    public ModelFactoryController() {
//        cargarDatosBase();

        cargarResourceXML();

        if(subasta == null){
            cargarDatosBase();
            guardarResourceXML();
        }


    }
    public void guardarCambios() {
        guardarResourceXML();
    }

    public void setAnunciante(UsuarioAnunciante anunciante){
        this.anunciante = anunciante;
    }
    public void setComprador(UsuarioComprador comprador) {
        this.comprador = comprador;
    }

    public UsuarioAnunciante obtenerAnunciante(String user) throws UsuarioException {
        return subasta.obtenerAnunciante(user);
    }
    public UsuarioComprador obtenerComprador(String user) throws UsuarioException {
        return subasta.obtenerComprador(user);
    }

    private void cargarDatosPrueba() {
        subasta = new Subasta();
        try {
            Persistencia.cargarDatosArchivo(subasta);
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
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
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
        if(anunciante.agregarProducto(ProductoMapper.productoDtoToProducto(productoDto))){
            Persistencia.guardarRegistroLog("Usuario: " + anunciante.getNombre() + " " + anunciante.getApellido(),1,"Registrar producto");
            return true;
        }
        return false;
    }

    public List<ProductoDto> obtenerProductos() {
        return ProductoMapper.getListProductos(anunciante.getListProductos());
    }

    public boolean eliminarProducto(ProductoDto productoSeleccionado) throws ProductoException {
        if(anunciante.eliminarProducto(ProductoMapper.productoDtoToProducto(productoSeleccionado))){
            registrarAccionesSistema("Usuario: " + anunciante.getNombre() + " " + anunciante.getApellido(),1,"Eliminar producto");
            return true;
        }
        return false;
    }

    public boolean actualizarProducto(ProductoDto productoSeleccionado, ProductoDto productoNuevo) throws ProductoException {
        if(anunciante.actualizarProducto(ProductoMapper.productoDtoToProducto(productoSeleccionado),
                ProductoMapper.productoDtoToProducto(productoNuevo))){
            registrarAccionesSistema("Usuario: " + anunciante.getNombre() + " " + anunciante.getApellido(),1,"Actualizar producto");
            return  true;
        }

        return false;
    }

        /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------ANUNCIOS--------------------------------------------------------------
     */

    public List<AnuncioDto> obtenerAnunciosActivos() {
        return AnuncioMapper.getListAnunciosDto(anunciante.getListAnunciosActivos());
    }

    public List<AnuncioDto> obtenerAnunciosInactivos() {
        return AnuncioMapper.getListAnunciosDto(anunciante.getListAnunciosInactivos());
    }

    public String[] obtenerProductosNombres() {
        List<Producto> listaProductos = anunciante.getListProductos();
        String[] nombres = new String[listaProductos.size()+1];
        nombres[0] = "Selecciona";
        for (int i = 0; i < listaProductos.size(); i++) {
            nombres[i+1] = listaProductos.get(i).getNombre();
        }

        return nombres;
    }

    public ProductoDto obtenerProductoSeleccionado(String nombreProducto) {
        List<Producto> listaProductos = anunciante.getListProductos();

        for (Producto producto: listaProductos) {
            if (producto.getNombre().equals(nombreProducto)){
                return ProductoMapper.productoToProductoDto(producto);
            }
        }
        return  null;
    }

    public boolean agregarAnuncio(AnuncioDto anuncioDto) throws AnuncioException {
        return anunciante.agregarAnuncio(AnuncioMapper.anuncioDtoToAnuncio(anuncioDto));
    }

    public boolean eliminarAnuncio(AnuncioDto elementoSeleccionado) throws AnuncioException {
        return anunciante.eliminarAnuncio(AnuncioMapper.anuncioDtoToAnuncio(elementoSeleccionado));
    }

    public boolean actualizarAnuncio(AnuncioDto elementoSeleccionado, AnuncioDto anuncioDto) throws AnuncioException {
        return anunciante.actualizarAnuncio(AnuncioMapper.anuncioDtoToAnuncio(elementoSeleccionado),
                AnuncioMapper.anuncioDtoToAnuncio(anuncioDto));
    }

    public List<PujaDto> obtenerListaPujasAnuncio(AnuncioDto anuncioDto) {
        return PujaMapper.getListPujas(AnuncioMapper.anuncioDtoToAnuncio(anuncioDto).getListPujas());
    }

    public void cerrarAnuncio(AnuncioDto anuncioSeleccionado, PujaDto pujaSeleccionada) throws AnuncioException {
        if(anunciante.getListAnuncios().contains(AnuncioMapper.anuncioDtoToAnuncio(anuncioSeleccionado))){
            Anuncio anuncio = AnuncioMapper.anuncioDtoToAnuncio(anuncioSeleccionado);
            anuncio.setIsActivo(false);

            List<Puja> listaPujas = anuncio.getListPujas();
            Puja puja = PujaMapper.pujaDtoToPuja(pujaSeleccionada);
            puja.setAceptada(true);
            int i = listaPujas.indexOf(puja);

            if (i != -1) {
                listaPujas.set(i, puja);
                anuncio.setListPujas(listaPujas);
            }

            anunciante.actualizarAnuncio(AnuncioMapper.anuncioDtoToAnuncio(anuncioSeleccionado),anuncio);
        }
    }

    public List<AnuncioDto> obtenerAnunciosSubasta() {
        return AnuncioMapper.getListAnunciosDto(subasta.getListAnuncios());
    }

    public List<PujaDto> obtenerPujasAnuncio(AnuncioDto anuncioSeleccionado) {
        return PujaMapper.getListPujas(AnuncioMapper.anuncioDtoToAnuncio(anuncioSeleccionado).getPujasUsuario(comprador));
    }

    public Boolean realizarPuja(PujaDto pujaDto, AnuncioDto anuncioSeleccionado) throws PujaException {
        if(comprador.realizarPuja(AnuncioMapper.anuncioDtoToAnuncio(anuncioSeleccionado), comprador)){
            int i = subasta.getListAnuncios().indexOf(AnuncioMapper.anuncioDtoToAnuncio(anuncioSeleccionado));
            subasta.getListAnuncios().get(i).getListPujas().add(PujaMapper.pujaDtoToPuja(pujaDto));
            return true;
        }
        return false;
    }

    public UsuarioCompradorDto obtenerCompradorDto() {
        return CompradorMapper.compradorToCompradorDto(comprador);
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
    --------------------------------------XML--------------------------------------------------------------
    */

    private void cargarResourceXML() {
        subasta = Persistencia.cargarRecursoBancoXML();
    }

    private void guardarResourceXML() {
        Persistencia.guardarRecursoSubastaXML(subasta);
    }


}
