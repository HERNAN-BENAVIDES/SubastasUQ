package co.edu.uniquindio.subastasuq.utils;

import co.edu.uniquindio.subastasuq.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SubastaUtils {

    public static Subasta inicializarDatos(){

        Subasta subasta = new Subasta();

        // Crear anunciantes
        UsuarioAnunciante anunciante1 = new UsuarioAnunciante("Dario", "Cordoba", "1234", 20, "dariobenavides42@gmail.com", "1234");
        UsuarioAnunciante anunciante2 = new UsuarioAnunciante("Ana", "Madrid", "5678", 25, "ana@example.com", "5678");
        UsuarioAnunciante anunciante3 = new UsuarioAnunciante("Juan", "Barcelona", "9012", 30, "juan@example.com", "9012");
        UsuarioAnunciante anunciante4 = new UsuarioAnunciante("Maria", "Valencia", "3456", 22, "maria@example.com", "3456");

        subasta.getListAnunciantes().add(anunciante1);
        subasta.getListAnunciantes().add(anunciante2);
        subasta.getListAnunciantes().add(anunciante3);
        subasta.getListAnunciantes().add(anunciante4);

        Producto producto1 = new Producto("TV", "Tecnologia", "12345", "Nuevo");
        Producto producto2 = new Producto("Moto", "Vehiculo", "12345", "Nuevo");
        Producto producto3 = new Producto("Bicicleta", "Deporte", "12345", "Nuevo");
        Producto producto4 = new Producto("Casa", "Bienraiz", "12345", "Nuevo");
        Producto producto5 = new Producto("Televisor", "Tecnologia", "12345", "Usado");
        Producto producto6 = new Producto("MotoGP", "Vehiculo", "12345", "Nuevo");
        Producto producto7 = new Producto("Bicicleta", "Deporte", "12345", "Nuevo");
        Producto producto8 = new Producto("Finca", "Bienraiz", "12345", "Nuevo");

        String fechaHoraString = "2023-11-07 21:00"; // La fecha y hora como una cadena
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));


        Anuncio anuncio = new Anuncio("Moto", "12", fechaHora, "MT09", "C:\\Users\\herna\\OneDrive\\Escritorio\\mt15.png", producto3, 50000.0);
        Anuncio anuncio1 = new Anuncio("Moto1", "12", fechaHora, "MT09", "C:\\Users\\herna\\OneDrive\\Escritorio\\mt15.png", producto2, 50000.0);
        Anuncio anuncio2 = new Anuncio("Moto2", "12", fechaHora, "MT09", "C:\\Users\\herna\\OneDrive\\Escritorio\\mt15.png", producto2, 50000.0);
        Anuncio anuncio3 = new Anuncio("Moto3", "12", fechaHora, "MT09", "C:\\Users\\herna\\OneDrive\\Escritorio\\mt15.png", producto2, 50000.0);
        Anuncio anuncio4 = new Anuncio("Moto4", "12", fechaHora, "MT09", "C:\\Users\\herna\\OneDrive\\Escritorio\\mt15.png", producto2, 50000.0);
        Anuncio anuncio5 = new Anuncio("Moto5", "12", fechaHora, "MT09", "C:\\Users\\herna\\OneDrive\\Escritorio\\mt15.png", producto2, 50000.0);

        anunciante1.getListProductos().add(producto1);
        anunciante1.getListProductos().add(producto2);

        anunciante2.getListProductos().add(producto3);
        anunciante2.getListProductos().add(producto4);
        anunciante2.getListAnuncios().add(anuncio);
        anunciante2.getListAnuncios().add(anuncio1);
        anunciante2.getListAnuncios().add(anuncio2);
        anunciante2.getListAnuncios().add(anuncio3);
        anunciante2.getListAnuncios().add(anuncio4);
        anunciante2.getListAnuncios().add(anuncio5);

        anunciante3.getListProductos().add(producto5);
        anunciante3.getListProductos().add(producto6);

        anunciante4.getListProductos().add(producto7);
        anunciante4.getListProductos().add(producto8);

        // Crear usuarios
        UsuarioComprador usuario1 = new UsuarioComprador("Elena", "Barcelona", "1111", 24, "elena@example.com", "abcd");
        UsuarioComprador usuario2 = new UsuarioComprador("Pedro", "Valencia", "2222", 28, "pedro@example.com", "efgh");
        UsuarioComprador usuario3 = new UsuarioComprador("Laura", "Madrid", "3333", 22, "laura@example.com", "ijkl");
        UsuarioComprador usuario4 = new UsuarioComprador("Luis", "Sevilla", "4444", 30, "luis@example.com", "mnop");

        subasta.getListCompradores().add(usuario1);
        subasta.getListCompradores().add(usuario2);
        subasta.getListCompradores().add(usuario3);
        subasta.getListCompradores().add(usuario4);

        return subasta;
    }

}
