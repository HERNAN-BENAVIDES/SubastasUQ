package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;

import java.util.List;

public class AnuncioController {
    ModelFactoryController modelFactoryController;

    public AnuncioController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public List<AnuncioDto> obtenerListaAnuncios() {
        return modelFactoryController.obtenerAnuncios();
    }

    public String[] obtenerProductos() {
        return modelFactoryController.obtenerProductosNombres();
    }
}
