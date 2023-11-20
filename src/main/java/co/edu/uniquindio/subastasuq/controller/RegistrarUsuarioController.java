package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.excepcions.NuevoAnuncianteExcepcion;
import co.edu.uniquindio.subastasuq.excepcions.NuevoCompradorException;
import co.edu.uniquindio.subastasuq.mapping.dto.UsuarioAnuncianteDto;
import co.edu.uniquindio.subastasuq.mapping.dto.UsuarioCompradorDto;
import co.edu.uniquindio.subastasuq.mapping.mappers.AnuncianteMapper;
import co.edu.uniquindio.subastasuq.mapping.mappers.CompradorMapper;

public class RegistrarUsuarioController {
    ModelFactoryController modelFactoryController;

    public RegistrarUsuarioController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public void guardarCambios() {
        modelFactoryController.guardarCambios();
    }

    public Boolean agregarNuevoComprador(UsuarioCompradorDto compradorDto) throws NuevoCompradorException {
        if(modelFactoryController.agregarComprador(compradorDto)){
            modelFactoryController.setComprador(CompradorMapper.compradorDtoToComprador(compradorDto));
            return true;
        }
        return false;
    }

    public boolean agregarNuevoAnunciante(UsuarioAnuncianteDto anuncianteDto) throws NuevoAnuncianteExcepcion {
        if(modelFactoryController.agregarAnunciante(anuncianteDto)){
            modelFactoryController.setAnunciante(AnuncianteMapper.anuncianteDtoToAnunciante(anuncianteDto));
            return true;
        }
        return false;
    }
}
