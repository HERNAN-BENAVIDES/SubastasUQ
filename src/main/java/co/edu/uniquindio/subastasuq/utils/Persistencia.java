package co.edu.uniquindio.subastasuq.utils;

import co.edu.uniquindio.subastasuq.excepcions.AutenticacionAnuncianteException;
import co.edu.uniquindio.subastasuq.excepcions.AutenticacionCompradorException;
import co.edu.uniquindio.subastasuq.excepcions.AutenticacionException;
import co.edu.uniquindio.subastasuq.model.*;
import com.opencsv.CSVWriter;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    public static void guardarAnunciante(UsuarioAnunciante anunciante) throws IOException {
        String contenido = anunciante.getNombre() + "@@" + anunciante.getApellido() + "@@" +
                anunciante.getCedula() + "@@" + anunciante.getEdad() + "@@" +
                anunciante.getUsername() + "@@" + anunciante.getPassword() + "\n";

        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_ANUNCIANTES, contenido, true);
    }


    public static void guardarComprador(UsuarioComprador comprador) throws IOException {
        String contenido = comprador.getNombre() + "@@" + comprador.getApellido() + "@@" +
                comprador.getCedula() + "@@" + comprador.getEdad() + "@@" +
                comprador.getUsername() + "@@" + comprador.getPassword() + "\n";

        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_COMPRADORES, contenido, true);
    }






    public static void cargarDatosArchivo(Subasta subasta) throws IOException {
        //List<UsuarioAnunciante> listAnunciantes = cargarAnunciantes();


 //      if (!listAnunciantes.isEmpty()){
 //          subasta.getListAnunciantes().addAll(listAnunciantes);
 //      }



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
            throws FileNotFoundException, IOException, AutenticacionException, AutenticacionAnuncianteException, AutenticacionCompradorException {
        switch (tipo) {
            case "Anunciante":
                if (validarAnunciante(usuario, contrasenia)) {
                    return true;
                } else {
                    throw new AutenticacionAnuncianteException("Credenciales de Anunciante incorrectas");
                }
            case "Comprador":
                if (validarComprador(usuario, contrasenia)) {
                    return true;
                } else {
                    throw new AutenticacionCompradorException("Credenciales de Comprador incorrectas");
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
    public static Boolean exportarCSV(List<Anuncio> anuncios, String rutaArchivo) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(rutaArchivo))) {
            // Escribir la cabecera si es necesario
            String[] header = {"Nombre", "Descripción", "Precio", "Fecha de Creación", "Hora final"};
            writer.writeNext(header);

            // Escribir los datos de los anuncios
            for (Anuncio anuncio : anuncios) {
                String[] data = {anuncio.getNombreAnuncio(), anuncio.getDescripcionAnuncio(), String.valueOf(anuncio.getPrecioInicial()), anuncio.getFechaFinal().toString(), anuncio.getHoraFinal().toString()};
                writer.writeNext(data);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // Manejar la excepción apropiadamente
        }
        return false;
    }
    public static Boolean exportarProductos(List<Producto> listProducto, String rutaArchivoCsv) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(rutaArchivoCsv))) {
            // Escribir la cabecera si es necesario
            String[] header = {"Nombre", "Codigo", "Tipo", "Estado"};
            writer.writeNext(header);

            // Escribir los datos de los anuncios
            for (Producto producto : listProducto) {
                String[] data = {producto.getNombre(), producto.getCodigo(), producto.getTipoProducto(), producto.getEstado()};
                writer.writeNext(data);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // Manejar la excepción apropiadamente
        }
        return false;
    }
}




