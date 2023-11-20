package co.edu.uniquindio.subastasuq.mapping.mappers;

import co.edu.uniquindio.subastasuq.mapping.dto.UsuarioAnuncianteDto;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;

public class AnuncianteMapper {

    public static UsuarioAnunciante anuncianteDtoToAnunciante(UsuarioAnuncianteDto anuncianteDto) {
        return new UsuarioAnunciante(anuncianteDto.nombre(), anuncianteDto.apellido(), anuncianteDto.cedula(),
                anuncianteDto.edad(), anuncianteDto.username(), anuncianteDto.password());
    }
}
