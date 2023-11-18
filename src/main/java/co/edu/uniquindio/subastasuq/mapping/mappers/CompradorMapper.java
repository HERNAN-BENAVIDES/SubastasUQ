package co.edu.uniquindio.subastasuq.mapping.mappers;

import co.edu.uniquindio.subastasuq.mapping.dto.UsuarioCompradorDto;
import co.edu.uniquindio.subastasuq.model.UsuarioComprador;

public class CompradorMapper {

    public static UsuarioComprador compradorDtoToComprador(UsuarioCompradorDto usuarioCompradorDto) {
        return new UsuarioComprador(usuarioCompradorDto.nombre(), usuarioCompradorDto.apellido(),
                usuarioCompradorDto.cedula(), usuarioCompradorDto.edad(), usuarioCompradorDto.username(),
                usuarioCompradorDto.password());
    }

    public static UsuarioCompradorDto compradorToCompradorDto(UsuarioComprador compradorAsociado) {
        return new UsuarioCompradorDto(compradorAsociado.getNombre(), compradorAsociado.getApellido(),
                compradorAsociado.getCedula(), compradorAsociado.getEdad(), compradorAsociado.getUsername(),
                compradorAsociado.getPassword());
    }
}
