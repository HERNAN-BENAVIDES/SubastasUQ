package co.edu.uniquindio.subastasuq.mapping.dto;

public record AnuncioDto(
        String nombreAnuncio,
        String codigoAnuncio,
        String descripcionAnuncio,
        String fotoAnuncio,
        ProductoDto productoAsociado,
        Double precioInicial,
        Double pujaMasAlta) {
}

