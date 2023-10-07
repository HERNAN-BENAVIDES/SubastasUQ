package co.edu.uniquindio.subastasuq.mapping.mappers;

import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.mapping.dto.UsuarioAnuncianteDto;
import co.edu.uniquindio.subastasuq.model.Producto;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;

import java.util.ArrayList;
import java.util.List;


public class UsuarioAnuncianteMapper {

    public static UsuarioAnuncianteDto usuarioToUsuarioDto(UsuarioAnunciante usuarioAnunciante) {
        if(usuarioAnunciante == null){
            return null;
        }

        return new UsuarioAnuncianteDto(usuarioAnunciante.getNombre(), usuarioAnunciante.getApellido(),
                usuarioAnunciante.getCedula(), usuarioAnunciante.getEdad(), usuarioAnunciante.getUsername(), usuarioAnunciante.getPassword());

    }
}
