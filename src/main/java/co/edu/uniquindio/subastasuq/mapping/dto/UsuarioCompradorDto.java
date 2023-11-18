package co.edu.uniquindio.subastasuq.mapping.dto;

public record UsuarioCompradorDto(
        String nombre,
        String apellido,
        String cedula,
        Integer edad,
        String username,
        String password
) {
}
