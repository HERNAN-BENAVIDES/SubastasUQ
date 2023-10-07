package co.edu.uniquindio.subastasuq.mapping.dto;

public record UsuarioAnuncianteDto(
        String nombre,
        String apellido,
        String cedula,
        Integer edad,
        String username,
        String password
) {
}
