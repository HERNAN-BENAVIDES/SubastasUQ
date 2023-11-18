package co.edu.uniquindio.subastasuq.mapping.mappers;

import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subastasuq.model.Anuncio;

import java.util.ArrayList;
import java.util.List;

import static co.edu.uniquindio.subastasuq.mapping.mappers.ProductoMapper.productoDtoToProducto;
import static co.edu.uniquindio.subastasuq.mapping.mappers.ProductoMapper.productoToProductoDto;

public class AnuncioMapper {
    public static Anuncio anuncioDtoToAnuncio(AnuncioDto anuncioDto) {
        if (anuncioDto == null) {
            return null;
        }

        Anuncio anuncio =  new Anuncio(
                anuncioDto.nombreAnuncio(),
                anuncioDto.codigoAnuncio(),
                anuncioDto.fechaFinal(), anuncioDto.descripcionAnuncio(),
                anuncioDto.fotoAnuncio(),
                productoDtoToProducto(anuncioDto.productoAsociado()),
                anuncioDto.precioInicial()
        );
        anuncio.setPujaMasAlta(anuncioDto.pujaMasAlta());
        anuncio.setIsActivo(anuncio.getIsActivo());
        anuncio.setListPujas(PujaMapper.setListPujas(anuncioDto.listPujas()));
        return anuncio;

    }

    public static AnuncioDto anuncioToAnuncioDto(Anuncio anuncio) {
        if (anuncio == null) {
            return null;
        }

        return new AnuncioDto(
                anuncio.getNombreAnuncio(),
                anuncio.getCodigoAnuncio(),
                anuncio.getFechaFinal(),
                anuncio.getDescripcionAnuncio(),
                anuncio.getFotoAnuncio(),
                productoToProductoDto(anuncio.getProductoAsociado()),
                anuncio.getPrecioInicial(),
                anuncio.getPujaMasAlta(),
                anuncio.getIsActivo(),
                anuncio.getTipoAnuncio(),
                PujaMapper.getListPujas(anuncio.getListPujas())
        );
    }

    public static List<AnuncioDto> getListAnunciosDto(List<Anuncio> listAnuncios) {
        List<AnuncioDto> listaAnunciosDto = new ArrayList<>();

        for (Anuncio anuncio : listAnuncios) {
            AnuncioDto anuncioDto = anuncioToAnuncioDto(anuncio);
            listaAnunciosDto.add(anuncioDto);
        }

        return listaAnunciosDto;
    }

}
