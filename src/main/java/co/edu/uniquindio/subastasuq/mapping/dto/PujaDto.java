package co.edu.uniquindio.subastasuq.mapping.dto;

import java.time.LocalDateTime;

public record PujaDto(
        LocalDateTime fecha,
        Double oferta,
        UsuarioCompradorDto compradorAsociado,
        Boolean isAceptada,
        AnuncioDto anuncioAsociado
) {
}
