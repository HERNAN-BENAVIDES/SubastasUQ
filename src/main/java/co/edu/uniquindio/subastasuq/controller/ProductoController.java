package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.excepcions.ProductoException;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;

import java.util.List;

public class ProductoController {
    ModelFactoryController modelFactoryController;

    public ProductoController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public boolean agregarProducto(ProductoDto productoDto) throws ProductoException {
        return modelFactoryController.agregarProducto(productoDto);
    }

    public List<ProductoDto> obtenerProductos() {
        return modelFactoryController.obtenerProductos();
    }

    public boolean eliminarProducto(ProductoDto productoSeleccionado) throws ProductoException {
        return  modelFactoryController.eliminarProducto(productoSeleccionado);
    }

    public boolean actualizarProducto(ProductoDto productoSeleccionado, ProductoDto productoNuevo) throws ProductoException {
        return modelFactoryController.actualizarProducto(productoSeleccionado, productoNuevo);
    }
}
