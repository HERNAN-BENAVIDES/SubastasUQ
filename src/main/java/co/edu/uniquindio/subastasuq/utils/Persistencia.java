package co.edu.uniquindio.subastasuq.utils;

import co.edu.uniquindio.subastasuq.excepcions.UsuarioException;
import co.edu.uniquindio.subastasuq.model.Producto;
import co.edu.uniquindio.subastasuq.model.Subasta;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;
import co.edu.uniquindio.subastasuq.model.UsuarioComprador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static void guardarAnunciantes(List<UsuarioAnunciante> listAnunciantes) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (UsuarioAnunciante anunciante : listAnunciantes) {
            contenido.append(anunciante.getNombre()).append("@@").append(anunciante.getApellido()).append("@@")
                    .append(anunciante.getCedula()).append("@@").append(anunciante.getEdad()).append("@@")
                    .append(anunciante.getUsername()).append("@@").append(anunciante.getPassword()).append("\n");
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_ANUNCIANTES, contenido.toString(), false);
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

    public static boolean iniciarSesion(String usuario, String contrasenia, String tipo) throws FileNotFoundException, IOException, UsuarioException {
        if(tipo.equals("Anunciante")){
            if(validarAnunciante(usuario,contrasenia)) {
                return true;
            }else {
                throw new UsuarioException("Anunciante no existe");
            }

        }else if(tipo.equals("Comprador")){
            if(validarComprador(usuario,contrasenia)) {
                return true;
            }else {
                throw new UsuarioException("Usuario no existe");
            }

        }else{
            throw new UsuarioException("Seleccione un tipo de usuario");
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




