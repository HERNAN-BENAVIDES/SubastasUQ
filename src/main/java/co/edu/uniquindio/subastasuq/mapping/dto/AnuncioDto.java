package co.edu.uniquindio.subastasuq.mapping.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record AnuncioDto(
        String nombreAnuncio,
        String codigoAnuncio,
        LocalDate fechaFinal,
        LocalTime horaFinal,
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

