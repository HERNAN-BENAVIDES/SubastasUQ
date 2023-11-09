package co.edu.uniquindio.subastasuq.mapping.dto;

import java.time.LocalDateTime;

public record PujaDto(
        LocalDateTime fecha,
        AnuncioDto anuncioAsociado,
        Double oferta
) {
}
