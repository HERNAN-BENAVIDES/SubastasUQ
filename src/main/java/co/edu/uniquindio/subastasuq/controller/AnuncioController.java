package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.excepcions.AnuncioException;
import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;

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

    public ProductoDto obtenerProductoSeleccionado(String nombreProducto) {
        return modelFactoryController.obtenerProductoSeleccionado(nombreProducto);
    }

    public boolean agregarAnuncio(AnuncioDto anuncioDto) throws AnuncioException {
        return modelFactoryController.agregarAnuncio(anuncioDto);
    }

    public boolean eliminarAnuncio(AnuncioDto elementoSeleccionado) throws AnuncioException {
        return modelFactoryController.eliminarAnuncio(elementoSeleccionado);
    }

    public boolean actualizarAnuncio(AnuncioDto elementoSeleccionado, AnuncioDto anuncioDto) throws AnuncioException {
        return modelFactoryController.actualizarAnuncio(elementoSeleccionado, anuncioDto);
    }
}
