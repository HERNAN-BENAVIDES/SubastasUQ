package co.edu.uniquindio.subastasuq.mapping.dto;

import java.time.LocalDateTime;

public record AnuncioDto(
        String nombreAnuncio,
        String codigoAnuncio,
        LocalDateTime fechaFinal,
        String descripcionAnuncio,
        String fotoAnuncio,
        ProductoDto productoAsociado,
        Double precioInicial,
        Double pujaMasAlta) {
}

