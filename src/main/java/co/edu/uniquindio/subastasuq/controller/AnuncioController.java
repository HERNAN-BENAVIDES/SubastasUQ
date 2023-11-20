package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.excepcions.AnuncioException;
import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.mapping.dto.PujaDto;
import co.edu.uniquindio.subastasuq.mapping.dto.UsuarioAnuncianteDto;

import java.util.List;

public class AnuncioController {
    ModelFactoryController modelFactoryController;

    /**
     * Constructor de la clase AnuncioController.
     */
    public AnuncioController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    /**
     * Obtiene una lista de anuncios disponibles.
     * @return Lista de objetos AnuncioDto.
     */
    public List<AnuncioDto> obtenerListaAnunciosActivos() {
        return modelFactoryController.obtenerAnunciosActivos();
    }

    public List<AnuncioDto> obtenerListaAnunciosInactivos() {
        return modelFactoryController.obtenerAnunciosInactivos();
    }

    /**
     * Obtiene una lista de nombres de productos disponibles.
     * @return Arreglo de nombres de productos.
     */
    public String[] obtenerProductos() {
        return modelFactoryController.obtenerProductosNombres();
    }

    /**
     * Obtiene un producto seleccionado por su nombre.
     * @param nombreProducto Nombre del producto a buscar.
     * @return Objeto ProductoDto correspondiente al nombre proporcionado.
     */
    public ProductoDto obtenerProductoSeleccionado(String nombreProducto) {
        return modelFactoryController.obtenerProductoSeleccionado(nombreProducto);
    }

    /**
     * Agrega un nuevo anuncio.
     * @param anuncioDto Objeto AnuncioDto a agregar.
     * @return True si la operación de agregar fue exitosa, de lo contrario, False.
     * @throws AnuncioException Si se produce un error al agregar el anuncio.
     */
    public boolean agregarAnuncio(AnuncioDto anuncioDto) throws AnuncioException {
        return modelFactoryController.agregarAnuncio(anuncioDto);
    }

    /**
     * Elimina un anuncio.
     * @param elementoSeleccionado Objeto AnuncioDto a eliminar.
     * @return True si la operación de eliminar fue exitosa, de lo contrario, False.
     * @throws AnuncioException Si se produce un error al eliminar el anuncio.
     */
    public boolean eliminarAnuncio(AnuncioDto elementoSeleccionado) throws AnuncioException {
        return modelFactoryController.eliminarAnuncio(elementoSeleccionado);
    }

    /**
     * Actualiza un anuncio existente.
     * @param elementoSeleccionado AnuncioDto existente a actualizar.
     * @param anuncioDto           Nuevo AnuncioDto con la información actualizada.
     * @return True si la operación de actualización fue exitosa, de lo contrario, False.
     * @throws AnuncioException Si se produce un error al actualizar el anuncio.
     */
    public boolean actualizarAnuncio(AnuncioDto elementoSeleccionado, AnuncioDto anuncioDto) throws AnuncioException {
        return modelFactoryController.actualizarAnuncio(elementoSeleccionado, anuncioDto);
    }

    /**
     * Obtiene una lista de pujas para un anuncio específico.
     * @param anuncioDto Objeto AnuncioDto para el cual se desean obtener las pujas.
     * @return Lista de objetos PujaDto relacionados con el anuncio.
     */
    public List<PujaDto> obtenerListaPujasAnuncio(AnuncioDto anuncioDto) {
        return modelFactoryController.obtenerListaPujasAnuncio(anuncioDto);
    }

    public void cerrarAnuncio(AnuncioDto anuncioSeleccionado, PujaDto pujaSeleccionada) throws AnuncioException {
        modelFactoryController.cerrarAnuncio(anuncioSeleccionado, pujaSeleccionada);
    }

    public Boolean exportarCvs(List<AnuncioDto> list, String rutaArchivoCsv) {
        return modelFactoryController.exportarCvs(list, rutaArchivoCsv);
    }

    public UsuarioAnuncianteDto getAnunciante() {
        return modelFactoryController.getAnunciante();
    }
}

