package co.edu.uniquindio.subastasuq.mapping.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AnuncioDto(
        String nombreAnuncio,
        String codigoAnuncio,
        LocalDateTime fechaFinal,
        String descripcionAnuncio,
        String fotoAnuncio,
        ProductoDto productoAsociado,
        Double precioInicial,
        Double pujaMasAlta,
        Boolean isActivo,
        String tipoAnuncio,
        List<PujaDto> listPujas
        ) {
}

