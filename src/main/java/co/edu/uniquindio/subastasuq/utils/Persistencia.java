package co.edu.uniquindio.subastasuq.utils;

import co.edu.uniquindio.subastasuq.excepcions.AutenticacionException;
import co.edu.uniquindio.subastasuq.excepcions.UsuarioException;
import co.edu.uniquindio.subastasuq.model.Producto;
import co.edu.uniquindio.subastasuq.model.Subasta;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;
import co.edu.uniquindio.subastasuq.model.UsuarioComprador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;


public class Persistencia {

    public static final String RUTA_ARCHIVO_PRODUCTOS = "src/main/resources/persistencia/archivoProductos.txt";
    public static final String RUTA_ARCHIVO_ANUNCIANTES = "src/main/resources/persistencia/archivoAnunciantes.txt";
    public static final String RUTA_ARCHIVO_COMPRADORES = "src/main/resources/persistencia/archivoCompradores.txt";
    public static final String RUTA_ARCHIVO_SUBASTA_XML = "src/main/resources/persistencia/Subasta.xml";
    public static final String RUTA_ARCHIVO_SUBASTA_BINARIO = "src/main/resources/persistencia/Subasta.dat";


    public static void guardarProductos(List<Producto> listProductos) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (Producto producto : listProductos) {
            contenido.append(producto.getNombre()).append("@@").append(producto.getTipoProducto()).
                    append("@@").append(producto.getCodigo()).append("@@").append(producto.getEstado()).append("\n");
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PRODUCTOS, contenido.toString(),false);
    }
    /*
        public static void guardarAnunciantes(List<UsuarioAnunciante> listAnunciantes) throws IOException {
            StringBuilder contenido = new StringBuilder();
            for (UsuarioAnunciante anunciante : listAnunciantes) {
                contenido.append(anunciante.getNombre()).append("@@").append(anunciante.getApellido()).append("@@")
                        .append(anunciante.getCedula()).append("@@").append(anunciante.getEdad()).append("@@")
                        .append(anunciante.getUsername()).append("@@").append(anunciante.getPassword()).append("\n");
            }
            ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_ANUNCIANTES, contenido.toString(), false);
        }


    */

    public static void guardarAnunciantes(List<UsuarioAnunciante> listAnunciantes) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (UsuarioAnunciante anunciante : listAnunciantes) {
            contenido.append(anunciante.getNombre()).append("@@").append(anunciante.getApellido()).append("@@")
                    .append(anunciante.getCedula()).append("@@").append(anunciante.getEdad()).append("@@")
                    .append(anunciante.getUsername()).append("@@").append(anunciante.getPassword()).append("\n");
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_ANUNCIANTES, contenido.toString(), false);
    }

    public static String guardarProductos(UsuarioAnunciante anunciante) {
        StringBuilder contenido = new StringBuilder();
        List<Producto> listProductos = anunciante.getListProductos(); // Obtiene la lista de productos del anunciante
        for (Producto producto : listProductos) {
            contenido.append(producto.getNombre()).append("@@").append(producto.getTipoProducto()).append("@@")
                    .append(producto.getCodigo()).append("@@").append(producto.getEstado()).append("\n");
        }
        return contenido.toString();
    }


    public static void guardarCompradores(List<UsuarioComprador> listCompradores) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (UsuarioComprador comprador : listCompradores) {
            contenido.append(comprador.getNombre()).append("@@").append(comprador.getApellido()).append("@@")
                    .append(comprador.getCedula()).append("@@").append(comprador.getEdad()).append("@@")
                    .append(comprador.getUsername()).append("@@").append(comprador.getPassword()).append("\n");
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_COMPRADORES, contenido.toString(), false);
    }

    /*

    public  static  void  guardarProductos (List <Producto> listProductos){
        StringBuilder  contenido = new StringBuilder();
        for ( Producto  producto : listProductos ) {
            contenido . append ( producto . getNombre ()). agregar ( "@@" ). append ( producto . getTipoProducto ()). agregar ( "@@" ). append ( producto.getCodigo ( )) . agregar ( "@@" ). append ( producto . getEstado ()). agregar ( " \n " );
        }
        ArchivoUtil.guardarArchivo ( RUTA_ARCHIVO_PRODUCTOS , contenido . toString (), false );
    }
     */

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


    public static void cargarDatosArchivo(Subasta subasta) throws IOException {
        List<UsuarioAnunciante> listAnunciantes = cargarAnunciantes();
        List<UsuarioComprador> listCompradores = cargarCompradores();

        if (!listAnunciantes.isEmpty()){
            subasta.getListAnunciantes().addAll(listAnunciantes);
        }

        if(!listCompradores.isEmpty()){
            subasta.getListCompradores().addAll(listCompradores);
        }
    }




    public static List<UsuarioAnunciante> cargarAnunciantes() throws IOException {
        List<UsuarioAnunciante> listAnunciantes = new ArrayList<UsuarioAnunciante>();
        List<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_ANUNCIANTES);

        for (String linea : contenido) {
            String[] partes = linea.split("@@");

            if (partes.length == 6) {
                String nombre = partes[0];
                String apellido = partes[1];
                String cedula = partes[2];
                Integer edad = Integer.parseInt(partes[3]);
                String username = partes[4];
                String password = partes[5];

                UsuarioAnunciante anunciante = new UsuarioAnunciante(nombre,apellido,cedula,edad,username,password);
                listAnunciantes.add(anunciante);
            }
        }
        return listAnunciantes;
    }
     /*

    public static List<UsuarioAnunciante> cargarAnunciantes() throws IOException {
        List<UsuarioAnunciante> listAnunciantes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO_ANUNCIANTES));
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split("@@");
            if (partes.length >= 7) {
                String nombre = partes[0];
                String apellido = partes[1];
                String cedula = partes[2];
                int edad = Integer.parseInt(partes[3]);
                String username = partes[4];
                String password = partes[5];

                // Llama al método para cargar los productos de este anunciante
                List<Producto> productos = cargarProductos(partes[6]);

                UsuarioAnunciante anunciante = new UsuarioAnunciante(nombre, apellido, cedula, edad, username, password);
                anunciante.setListProductos(productos);
                listAnunciantes.add(anunciante);
                System.out.println(anunciante.toString());
            }
        }

        br.close();
        return listAnunciantes;
    }

    public static List<Producto> cargarProductos(String productosStr) {
        List<Producto> productos = new ArrayList<>();
        String[] productosPartes = productosStr.split("##");
        for (String productoParte : productosPartes) {
            String[] productoInfo = productoParte.split("@@");
            if (productoInfo.length == 4) {
                String nombreProducto = productoInfo[0];
                String tipoProducto = productoInfo[1];
                String codigo = productoInfo[2];
                String estado = productoInfo[3];
                Producto producto = new Producto(nombreProducto, tipoProducto, codigo, estado);
                productos.add(producto);
            }
        }
        return productos;
    }
    */

    public static List<UsuarioComprador> cargarCompradores() throws IOException {
        List<UsuarioComprador> listCompradores = new ArrayList<UsuarioComprador>();
        List<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_COMPRADORES);

        for (String linea : contenido) {
            String[] partes = linea.split("@@");

            if (partes.length == 6) {
                String nombre = partes[0];
                String apellido = partes[1];
                String cedula = partes[2];
                Integer edad = Integer.parseInt(partes[3]);
                String username = partes[4];
                String password = partes[5];

                UsuarioComprador comprador = new UsuarioComprador(nombre, apellido, cedula, edad, username, password);
                listCompradores.add(comprador);
            }
        }
        return listCompradores;
    }

    public static boolean iniciarSesion(String usuario, String contrasenia, String tipo)
            throws FileNotFoundException, IOException, AutenticacionException {
        switch (tipo) {
            case "Anunciante":
                if (validarAnunciante(usuario, contrasenia)) {
                    return true;
                } else {
                    throw new AutenticacionException("Credenciales de Anunciante incorrectas");
                }
            case "Comprador":
                if (validarComprador(usuario, contrasenia)) {
                    return true;
                } else {
                    throw new AutenticacionException("Credenciales de Comprador incorrectas");
                }
            default:
                throw new AutenticacionException("Seleccione un tipo de usuario válido");
        }
    }


    private static boolean validarComprador(String usuario, String contrasenia) throws FileNotFoundException, IOException {
        List<UsuarioComprador> compradores = cargarCompradores();

        for (UsuarioComprador compradorAux : compradores) {
            if (compradorAux.getUsername().equals(usuario) && compradorAux.getPassword().equals(contrasenia)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validarAnunciante(String usuario, String contrasenia) throws FileNotFoundException, IOException {
        List<UsuarioAnunciante> anunciantes = cargarAnunciantes();
        for (UsuarioAnunciante anuncianteAux : anunciantes) {
            if (anuncianteAux.getUsername().equals(usuario) && anuncianteAux.getPassword().equals(contrasenia)) {
                return true;
            }
        }
        return false;
    }



}




