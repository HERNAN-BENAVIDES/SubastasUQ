package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;

import java.util.Collection;

public class CompradorController {
    ModelFactoryController modelFactoryController;

    public CompradorController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public Collection<AnuncioDto> obtenerListaAnuncios() {
        return modelFactoryController.obtenerAnunciosSubasta();
    }
}
