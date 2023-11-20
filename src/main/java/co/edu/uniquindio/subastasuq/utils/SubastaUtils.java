package co.edu.uniquindio.subastasuq.utils;

import co.edu.uniquindio.subastasuq.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class SubastaUtils {

    public static Subasta inicializarDatos(){

        Subasta subasta = new Subasta();

        // Crear usuarios
        UsuarioComprador comprador = new UsuarioComprador("Elena", "Barcelona", "1111", 24, "elena@example.com", "abcd");
        UsuarioComprador comprador1 = new UsuarioComprador("Pedro", "Valencia", "2222", 28, "pedro@example.com", "efgh");

        subasta.getListCompradores().add(comprador);
        subasta.getListCompradores().add(comprador1);

        // Crear anunciantes
        UsuarioAnunciante anunciante = new UsuarioAnunciante("Ana", "Madrid", "5678", 25, "ana@example.com", "5678");
        UsuarioAnunciante anunciante1 = new UsuarioAnunciante("Juan", "Barcelona", "9012", 30, "juan@example.com", "9012");

        Producto producto1 = new Producto("TV", "Tecnologia", "12345", "Nuevo", anunciante);
        Producto producto2 = new Producto("Moto", "Vehiculo", "12345", "Nuevo", anunciante);
        Producto producto3 = new Producto("Bicicleta", "Deporte", "12345", "Nuevo", anunciante);
        Producto producto4 = new Producto("Casa", "Bien raiz", "12345", "Nuevo", anunciante);


        Anuncio anuncio = new Anuncio("Moto", "12", LocalDate.of(2023, 12, 24), LocalTime.of(21,0),"MT09", "C:\\Users\\herna\\OneDrive\\Escritorio\\mt15.png", producto2, anunciante,   160000.0);
        Anuncio anuncio1 = new Anuncio("Moto1", "12", LocalDate.of(2023, 12, 24),LocalTime.of(21,0), "MT09", "C:\\Users\\herna\\OneDrive\\Escritorio\\mt15.png", producto2, anunciante, 50000.0);
        Anuncio anuncio2 = new Anuncio("Moto2", "12", LocalDate.of(2023, 12, 24),LocalTime.of(21,0), "MT09", "C:\\Users\\herna\\OneDrive\\Escritorio\\mt15.png", producto2,anunciante,  50000.0);

        anuncio.setIsActivo(false);
   //     anuncio1.setIsActivo(true);
   //     anuncio2.setIsActivo(true);

        Puja puja = new Puja(300000.0,  comprador, anuncio);
        puja.setAceptada(true);
        Puja puja1 = new Puja(200000.0,  comprador, anuncio);
        Puja puja2 = new Puja(250000.0,  comprador1, anuncio1);

        anuncio.getListPujas().add(puja);
        anuncio.getListPujas().add(puja1);

        anuncio1.getListPujas().add(puja1);
        anuncio1.getListPujas().add(puja2);

        anunciante.getListProductos().add(producto1);
        anunciante.getListProductos().add(producto2);
        anunciante.getListAnuncios().add(anuncio);
        anunciante.getListAnuncios().add(anuncio1);
        anunciante.getListAnuncios().add(anuncio2);

        subasta.getListAnunciantes().add(anunciante);

        anunciante1.getListProductos().add(producto3);
        anunciante1.getListProductos().add(producto4);

        subasta.getListAnunciantes().add(anunciante1);

        return subasta;
    }

}
