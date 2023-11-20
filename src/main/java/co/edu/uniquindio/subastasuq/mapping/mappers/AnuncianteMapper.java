package co.edu.uniquindio.subastasuq.mapping.mappers;

import co.edu.uniquindio.subastasuq.mapping.dto.UsuarioAnuncianteDto;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;

public class AnuncianteMapper {

    public static UsuarioAnunciante anuncianteDtoToAnunciante(UsuarioAnuncianteDto anuncianteDto) {
        return new UsuarioAnunciante(anuncianteDto.nombre(), anuncianteDto.apellido(), anuncianteDto.cedula(),
                anuncianteDto.edad(), anuncianteDto.username(), anuncianteDto.password());
    }

    public static UsuarioAnuncianteDto anuncianteToAnuncianteDto(UsuarioAnunciante anunciante) {
        return new UsuarioAnuncianteDto(anunciante.getNombre(), anunciante.getApellido(), anunciante.getCedula(),
                anunciante.getEdad(), anunciante.getUsername(), anunciante.getPassword());
    }
}
