package co.edu.uniquindio.subastasuq.mapping.mappers;

import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.model.Producto;

import java.util.ArrayList;
import java.util.List;


public class UsuarioAnuncianteMapper {

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

    public static List<ProductoDto> getListProductos(List<Producto> listProductos) {
        List<ProductoDto> listaProductosDto = new ArrayList<>();

        for (Producto producto : listProductos) {
            ProductoDto productoDto = new ProductoDto(
                    producto.getNombre(),
                    producto.getTipoProducto(),
                    producto.getCodigo(),
                    producto.getEstado()
            );
            listaProductosDto.add(productoDto);
        }

        return listaProductosDto;
    }
}
