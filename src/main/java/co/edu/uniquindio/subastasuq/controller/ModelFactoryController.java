package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.config.RabbitFactory;
import co.edu.uniquindio.subastasuq.excepcions.*;
import co.edu.uniquindio.subastasuq.hilos.*;
import co.edu.uniquindio.subastasuq.mapping.dto.*;
import co.edu.uniquindio.subastasuq.mapping.mappers.*;
import co.edu.uniquindio.subastasuq.model.*;
import co.edu.uniquindio.subastasuq.utils.Constantes;
import co.edu.uniquindio.subastasuq.utils.Persistencia;
import co.edu.uniquindio.subastasuq.utils.SubastaUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class ModelFactoryController {

    RabbitFactory rabbitFactory;
    ConnectionFactory connectionFactory;

    CrearAnuncioThread crearAnuncioThread;
    EliminarAnuncioThread eliminarAnuncioThread;
    ActualizarAnuncioThread actualizarAnuncioThread;

    CrearProductoThread crearProductoThread;
    EliminarProductoThread eliminarProductoThread;
    ActualizarProductoThread actualizarProductoThread;

    CrearPujaThread crearPujaThread;
    ActualizarPujaThread actualizarPujaThread;


    private Subasta subasta;
    private UsuarioAnunciante anunciante;
    private UsuarioComprador comprador;

    public ModelFactoryController() {
        initRabbitConnection();
        cargarResourceXML();
        consumirMensajes();

        if(subasta == null){
            cargarDatosBase();
            guardarResourceXML();
        }
    }

    private void consumirMensajes() {
        crearAnuncioThread  = new CrearAnuncioThread(connectionFactory);
        eliminarAnuncioThread  = new EliminarAnuncioThread(connectionFactory);
        actualizarAnuncioThread  = new ActualizarAnuncioThread(connectionFactory);

        crearProductoThread  = new CrearProductoThread(connectionFactory);
        eliminarProductoThread  = new EliminarProductoThread(connectionFactory);
        actualizarProductoThread  = new ActualizarProductoThread(connectionFactory);

        crearPujaThread = new CrearPujaThread(connectionFactory);
        actualizarPujaThread = new ActualizarPujaThread(connectionFactory);

       iniciarHilos();


       registrarAccionesSistema("Consumir mensajes", 1, "Consumir mensajes desde colas RabbitMQ");

    }

    private void iniciarHilos() {
        // Iniciar los hilos relacionados con anuncios
//       crearAnuncioThread.start();
//       eliminarAnuncioThread.start();
//       actualizarAnuncioThread.start();
//
//       // Iniciar los hilos relacionados con productos
//       crearProductoThread.start();
//       eliminarProductoThread.start();
//       actualizarProductoThread.start();
//
//       // Iniciar los hilos relacionados con pujas
//       crearPujaThread.start();
        actualizarPujaThread.start();

    }

    public void guardarCambios() {
        guardarResourceXML();
        registrarAccionesSistema("Guardar cambios", 1, "Guardar cambios en la aplicación");

    }

    public void setAnunciante(UsuarioAnunciante anunciante){
        this.anunciante = anunciante;
    }
    public void setComprador(UsuarioComprador comprador) {
        this.comprador = comprador;
    }

    public UsuarioAnunciante obtenerAnunciante(String user) throws UsuarioAnuncianteException {
        return subasta.obtenerAnunciante(user);
    }
    public UsuarioComprador obtenerComprador(String user) throws UsuarioCompradorException {
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

    public UsuarioAnuncianteDto getAnunciante() {
        return AnuncianteMapper.anuncianteToAnuncianteDto(anunciante);
    }

    private void cargarDatosBase() {
        subasta = SubastaUtils.inicializarDatos();
    }

    public Boolean exportarCvs(List<AnuncioDto> list, String rutaArchivoCsv) {
        return Persistencia.exportarCSV(AnuncioMapper.getListAnuncios(list), rutaArchivoCsv);
    }

    public Boolean exportarProductos(List<Producto> listProducto, String rutaArchivoCsv) {
        return Persistencia.exportarProductos(listProducto,rutaArchivoCsv);
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
    --------------------------------------RABBIT--------------------------------------------------------------
     */


    private void initRabbitConnection() {
        rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("conexion establecidad");
    }

    private void enviarObjeto(Object objeto, String cola) {
        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
            // Declara la cola si no existe
            channel.queueDeclare(cola, false, false, false, null);

            // Serializa el objeto
            byte[] datosSerializados = serializarObjeto(objeto);

            // Envía el objeto serializado a la cola
            channel.basicPublish("", cola, null, datosSerializados);
            System.out.println(" [x] Enviado '" + objeto.toString() + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] serializarObjeto(Object objeto) throws Exception {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(objeto);
            return bos.toByteArray();
        }
    }


    /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------INICIO SESION--------------------------------------------------------------
     */

    public Boolean agregarComprador(UsuarioCompradorDto compradorDto) throws NuevoCompradorException {
        return subasta.agregarUsuarioComprador(CompradorMapper.compradorDtoToComprador(compradorDto));
    }

    public boolean agregarAnunciante(UsuarioAnuncianteDto anuncianteDto) throws NuevoAnuncianteExcepcion {
        return subasta.agregarUsuarioAnunciante(AnuncianteMapper.anuncianteDtoToAnunciante(anuncianteDto));
    }



    /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------INICIO SESION--------------------------------------------------------------
     */

    public boolean iniciarSesion(String user, String password, String tipo) throws IOException, AutenticacionException, AutenticacionCompradorException, AutenticacionAnuncianteException {
        if(Persistencia.iniciarSesion(user, password, tipo)){
            registrarAccionesSistema("Inicio de sesión", 1, "Inicio de sesión: user: " + user);
            return true;
        }
        return false;
    }



    /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------PRODUCTOS--------------------------------------------------------------
     */

    public List<ProductoDto> obtenerProductos() {
        return ProductoMapper.getListProductos(anunciante.getListProductos());
    }

    // Método para agregar un producto
    public boolean agregarProducto(ProductoDto productoDto) throws ProductoException {
        if (anunciante.agregarProducto(ProductoMapper.productoDtoToProducto(productoDto))) {
            registrarAccionesSistema("Registrar producto", 1, "Usuario: " + anunciante.getNombre() + " " + anunciante.getApellido());
            enviarObjeto(ProductoMapper.productoDtoToProducto(productoDto), Constantes.CREAR_PRODUCTO_QUEUE);
            return true;
        }
        return false;
    }

    // Método para eliminar un producto
    public boolean eliminarProducto(ProductoDto productoSeleccionado) throws ProductoException {
        if (anunciante.eliminarProducto(ProductoMapper.productoDtoToProducto(productoSeleccionado))) {
            registrarAccionesSistema("Usuario: " + anunciante.getNombre() + " " + anunciante.getApellido(), 1, "Eliminar producto");
            enviarObjeto(ProductoMapper.productoDtoToProducto(productoSeleccionado), Constantes.ELIMINAR_PRODUCTO_QUEUE); // Enviar mensaje a la cola de productos
            return true;
        }
        return false;
    }

    // Método para actualizar un producto
    public boolean actualizarProducto(ProductoDto productoSeleccionado, ProductoDto productoNuevo) throws ProductoException {
        if (anunciante.actualizarProducto(
                ProductoMapper.productoDtoToProducto(productoSeleccionado),
                ProductoMapper.productoDtoToProducto(productoNuevo))) {
            registrarAccionesSistema("Usuario: " + anunciante.getNombre() + " " + anunciante.getApellido(), 1, "Actualizar producto");
            enviarObjeto(productoNuevo, Constantes.ACTUALIZAR_PRODUCTO_QUEUE); // Enviar mensaje a la cola de productos
            return true;
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
        if(anunciante.agregarAnuncio(AnuncioMapper.anuncioDtoToAnuncio(anuncioDto))){
            registrarAccionesSistema("Usuario: " + anunciante.getNombre() + " " + anunciante.getApellido(), 1, "Registrar anuncio");
            enviarObjeto(AnuncioMapper.anuncioDtoToAnuncio(anuncioDto), Constantes.CREAR_ANUNCIO_QUEUE);
            return true;
        }
        return false;
    }

    public boolean eliminarAnuncio(AnuncioDto elementoSeleccionado) throws AnuncioException {
        if(anunciante.eliminarAnuncio(AnuncioMapper.anuncioDtoToAnuncio(elementoSeleccionado))){
            enviarObjeto(AnuncioMapper.anuncioDtoToAnuncio(elementoSeleccionado), Constantes.ELIMINAR_ANUNCIO_QUEUE);
            registrarAccionesSistema("Usuario: " + anunciante.getNombre() + " " + anunciante.getApellido(), 1, "Eliminar anuncio");
            return true;
        }
        return false;
    }

    public boolean actualizarAnuncio(AnuncioDto elementoSeleccionado, AnuncioDto anuncioDto) throws AnuncioException {
        if(anunciante.actualizarAnuncio(AnuncioMapper.anuncioDtoToAnuncio(elementoSeleccionado),
                AnuncioMapper.anuncioDtoToAnuncio(anuncioDto))){
            enviarObjeto(AnuncioMapper.anuncioDtoToAnuncio(anuncioDto), Constantes.ACTUALIZAR_ANUNCIO_QUEUE);
            registrarAccionesSistema("Usuario: " + anunciante.getNombre() + " " + anunciante.getApellido(), 1, "Actualizar anuncio");
            return true;
        }
        return false;
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
            registrarAccionesSistema("Usuario: " + anunciante.getNombre() + " " + anunciante.getApellido(), 1, "Cerrar anuncio");
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
            enviarObjeto(PujaMapper.pujaDtoToPuja(pujaDto), Constantes.CREAR_PUJA_QUEUE);
            registrarAccionesSistema("Usuario: " + comprador.getNombre() + " " + comprador.getApellido(), 1, "Realizar puja");
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
