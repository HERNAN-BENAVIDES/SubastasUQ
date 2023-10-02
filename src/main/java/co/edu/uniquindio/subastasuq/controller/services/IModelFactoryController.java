package co.edu.uniquindio.subastasuq.controller.services;

import co.edu.uniquindio.subastasuq.excepcions.ProductoException;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;

public interface IModelFactoryController {
    boolean agregarProducto(ProductoDto productoDto) throws ProductoException;
}
