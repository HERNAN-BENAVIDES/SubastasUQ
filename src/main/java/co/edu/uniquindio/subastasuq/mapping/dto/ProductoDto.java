package co.edu.uniquindio.subastasuq.mapping.dto;

public record ProductoDto(
     String nombre,
     String tipoProducto,
     String codigo,
     String estado,
     UsuarioAnuncianteDto anuncianteAsociado
){
}
