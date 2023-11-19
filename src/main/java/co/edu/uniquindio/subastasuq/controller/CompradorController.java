package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.excepcions.PujaException;
import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subastasuq.mapping.dto.PujaDto;
import co.edu.uniquindio.subastasuq.mapping.dto.UsuarioCompradorDto;

import java.util.Collection;
import java.util.List;

public class CompradorController {
    ModelFactoryController modelFactoryController;

    public CompradorController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public Collection<AnuncioDto> obtenerListaAnuncios() {
        return modelFactoryController.obtenerAnunciosSubasta();
    }

    public List<PujaDto> obtenerListaPujasAnuncio(AnuncioDto anuncioSeleccionado) {
        return modelFactoryController.obtenerPujasAnuncio(anuncioSeleccionado);
    }

    public Boolean realizarPuja(PujaDto pujaDto, AnuncioDto anuncioSeleccionado) throws PujaException {
        return modelFactoryController.realizarPuja(pujaDto, anuncioSeleccionado);
    }

    public UsuarioCompradorDto obtenerCompradorDto() {
        return  modelFactoryController.obtenerCompradorDto();
    }
}
