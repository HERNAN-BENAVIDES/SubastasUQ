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
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;


public class Persistencia {

    public static final String RUTA_ARCHIVO_PRODUCTOS = "src/main/resources/persistencia/archivos/archivoProductos.txt";
    public static final String RUTA_ARCHIVO_ANUNCIANTES = "src/main/resources/persistencia/archivos/archivoAnunciantes.txt";
    public static final String RUTA_ARCHIVO_ANUNCIANTES2 = "src/main/resources/persistencia/archivos/archivoAnunciantes2.txt";
    public static final String RUTA_ARCHIVO_COMPRADORES = "src/main/resources/persistencia/archivos/archivoCompradores.txt";
    public static final String RUTA_ARCHIVO_SUBASTA_XML = "src/main/resources/persistencia/Subasta.xml";
    public static final String RUTA_ARCHIVO_SUBASTA_BINARIO = "src/main/resources/persistencia/Subasta.dat";
    private static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/SubastaLog.txt";
    private static final String RUTA_ARCHIVO_LOG_RESPALDO = "src/main/resources/persistencia/respaldo/SubastaLogRespaldo.txt";
    private static final String RUTA_ARCHIVO_ANUNCIANTES_RESPALDO = "src/main/resources/persistencia/respaldo/archivoAnunciantesRespaldo.txt";
    private static final String RUTA_ARCHIVO_ANUNCIANTES2_RESPALDO = "src/main/resources/persistencia/respaldo/archivoAnunciantes2Respaldo.txt";
    private static final String RUTA_ARCHIVO_COMPRADORES_RESPALDO = "src/main/resources/persistencia/respaldo/archivoCompradoresRespaldo.txt";


    public static void guardarProductos(List<Producto> listProductos) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (Producto producto : listProductos) {
            contenido.append(producto.getNombre()).append("@@").append(producto.getTipoProducto()).
                    append("@@").append(producto.getCodigo()).append("@@").append(producto.getEstado()).append("\n");
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PRODUCTOS, contenido.toString(),false);
    }

    public static void guardarAnunciantes(List<UsuarioAnunciante> listAnunciantes) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (UsuarioAnunciante anunciante : listAnunciantes) {
            contenido.append(anunciante.getNombre()).append("@@").append(anunciante.getApellido()).append("@@")
                    .append(anunciante.getCedula()).append("@@").append(anunciante.getEdad()).append("@@")
                    .append(anunciante.getUsername()).append("@@").append(anunciante.getPassword()).append("\n");
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_ANUNCIANTES, contenido.toString(), false);
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_ANUNCIANTES_RESPALDO, contenido.toString(), false);
    }


    public static void guardarCompradores(List<UsuarioComprador> listCompradores) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (UsuarioComprador comprador : listCompradores) {
            contenido.append(comprador.getNombre()).append("@@").append(comprador.getApellido()).append("@@")
                    .append(comprador.getCedula()).append("@@").append(comprador.getEdad()).append("@@")
                    .append(comprador.getUsername()).append("@@").append(comprador.getPassword()).append("\n");
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_COMPRADORES, contenido.toString(), false);
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_COMPRADORES_RESPALDO, contenido.toString(), false);
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


    public static void cargarDatosArchivo(Subasta subasta) throws IOException {
        //List<UsuarioAnunciante> listAnunciantes = cargarAnunciantes();
        List<UsuarioAnunciante> listAnunciantes2 = cargarAnunciantes2();

 //      if (!listAnunciantes.isEmpty()){
 //          subasta.getListAnunciantes().addAll(listAnunciantes);
 //      }

        if (!listAnunciantes2.isEmpty()){
            subasta.getListAnunciantes().addAll(listAnunciantes2);
        }

 //       if(!listCompradores.isEmpty()){
 //           subasta.getListCompradores().addAll(listCompradores);
 //       }
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


    public static void guardarAnunciantes2(List<UsuarioAnunciante> listAnunciantes) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (UsuarioAnunciante anunciante : listAnunciantes) {
            contenido.append(anunciante.getNombre()).append("@@").append(anunciante.getApellido()).append("@@")
                    .append(anunciante.getCedula()).append("@@").append(anunciante.getEdad()).append("@@")
                    .append(anunciante.getUsername()).append("@@").append(anunciante.getPassword()).append("##");

            // Llama al método para obtener y guardar los productos de este anunciante
            contenido.append(guardarProductos2(anunciante));

            contenido.append("\n");
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_ANUNCIANTES2, contenido.toString(), false);
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_ANUNCIANTES2_RESPALDO, contenido.toString(), false);

    }

    public static String guardarProductos2(UsuarioAnunciante anunciante) {
        StringBuilder contenido = new StringBuilder();
        List<Producto> listProductos = anunciante.getListProductos();
        for (int i = 0; i < listProductos.size(); i++) {
            if (i < listProductos.size()-1) {
                contenido.append(listProductos.get(i).getNombre()).append("@@").append(listProductos.get(i).getTipoProducto()).append("@@")
                        .append(listProductos.get(i).getCodigo()).append("@@").append(listProductos.get(i).getEstado()).append("##");
            }
            if (i == listProductos.size()-1) {
                contenido.append(listProductos.get(i).getNombre()).append("@@").append(listProductos.get(i).getTipoProducto()).append("@@")
                        .append(listProductos.get(i).getCodigo()).append("@@").append(listProductos.get(i).getEstado());
            }
        }
        return contenido.toString();
    }


    public static List<UsuarioAnunciante> cargarAnunciantes2() throws IOException {
        List<UsuarioAnunciante> listAnunciantes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO_ANUNCIANTES2));
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split("##");

            if (partes.length == 1){
                UsuarioAnunciante anunciante = crearAnunciante(partes[0]);
                listAnunciantes.add(anunciante);
            }

            if (partes.length >= 2) {
                String infoAnunciante = partes[0];
                String[] productoInfo = Arrays.copyOfRange(partes, 1, partes.length);

                UsuarioAnunciante anunciante = crearAnunciante(infoAnunciante);
                List<Producto> productos = crearProductos(productoInfo);

                anunciante.setListProductos(productos);
                listAnunciantes.add(anunciante);
            }

        }

        br.close();
        return listAnunciantes;
    }

    public static UsuarioAnunciante crearAnunciante(String infoAnuncianteStr) {
        String[] infoAnunciante = infoAnuncianteStr.split("@@");
        if (infoAnunciante.length >= 6) {
            String nombre = infoAnunciante[0];
            String apellido = infoAnunciante[1];
            String cedula = infoAnunciante[2];
            int edad = Integer.parseInt(infoAnunciante[3]);
            String username = infoAnunciante[4];
            String password = infoAnunciante[5];
            return new UsuarioAnunciante(nombre, apellido, cedula, edad, username, password);
        }
        return null; // Manejo de errores opcional
    }

    public static List<Producto> crearProductos(String[] productoInfoStrs) {
        List<Producto> productos = new ArrayList<>();
        for (String productoInfoStr : productoInfoStrs) {
            String[] productoInfo = productoInfoStr.split("@@");
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


    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }

/*
-----------------------------------------------------------------------------------------------------------
--------------------------------------BINARIO--------------------------------------------------------------
 */
    public static void guardarRecursoBancoBinario(Subasta subasta) {
        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_SUBASTA_BINARIO,subasta);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Subasta cargarRecursoBancoBinario() {
        Subasta subasta = null;

        try {
            subasta = (Subasta) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_SUBASTA_BINARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subasta;
    }

    /*
-----------------------------------------------------------------------------------------------------------
--------------------------------------XML--------------------------------------------------------------
 */

    public static Subasta cargarRecursoBancoXML() {
        Subasta subasta = null;

        try {
            subasta = (Subasta) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_SUBASTA_XML);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subasta;
    }


    public static void guardarRecursoSubastaXML(Subasta subasta) {

        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_SUBASTA_XML, subasta);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}




