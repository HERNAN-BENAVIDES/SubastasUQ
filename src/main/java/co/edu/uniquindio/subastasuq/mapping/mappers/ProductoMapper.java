package co.edu.uniquindio.subastasuq.mapping.mappers;

import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoMapper {
    public static Producto productoDtoToProducto(ProductoDto productoDto) {
        if (productoDto == null) {
            return null;
        }

        Producto producto = new Producto();
        producto.setNombre(productoDto.nombre());
        producto.setCodigo(productoDto.codigo());
        producto.setTipoProducto(productoDto.tipoProducto());
        producto.setEstado(productoDto.estado());

        return producto;
    }


    public static ProductoDto productoToProductoDto(Producto producto) {
        if (producto == null) {
            return null;
        }

        return new ProductoDto(producto.getNombre(), producto.getTipoProducto(), producto.getCodigo(),
                producto.getEstado()
        );
    }

    public static List<ProductoDto> getListProductos(List<Producto> listProductos) {
        List<ProductoDto> listaProductosDto = new ArrayList<>();

        for (Producto producto : listProductos) {
            ProductoDto productoDto = productoToProductoDto(producto);
            listaProductosDto.add(productoDto);
        }

        return listaProductosDto;
    }
}
