package co.edu.uniquindio.subastasuq.mapping.dto;

import co.edu.uniquindio.subastasuq.model.TipoProducto;

public record ProductoDto(
     String nombre,
     TipoProducto tipoProducto,
     String codigo,
     String estado
){
}
