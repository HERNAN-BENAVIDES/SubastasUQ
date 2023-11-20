package co.edu.uniquindio.subastasuq.mapping.mappers;

import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subastasuq.model.Anuncio;
import co.edu.uniquindio.subastasuq.model.Puja;

import java.util.ArrayList;
import java.util.List;



public class AnuncioMapper {
    public static Anuncio anuncioDtoToAnuncio(AnuncioDto anuncioDto) {
        if (anuncioDto == null) {
            return null;
        }

        Anuncio anuncio =  new Anuncio(
                anuncioDto.nombreAnuncio(),
                anuncioDto.codigoAnuncio(),
                anuncioDto.fechaFinal(),
                anuncioDto.horaFinal(),
                anuncioDto.descripcionAnuncio(),
                anuncioDto.fotoAnuncio(),
                ProductoMapper.productoDtoToProducto(anuncioDto.productoAsociado()),
                AnuncianteMapper.anuncianteDtoToAnunciante(anuncioDto.anuncianteAsociado()),
                anuncioDto.precioInicial()
        );
        anuncio.setPujaMasAlta(anuncioDto.pujaMasAlta());
        anuncio.setIsActivo(anuncioDto.isActivo());
        List<Puja> list = PujaMapper.setListPujas(anuncioDto.listPujas());
        anuncio.setListPujas(list);
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
                anuncio.getHoraFinal(),
                anuncio.getDescripcionAnuncio(),
                anuncio.getFotoAnuncio(),
                ProductoMapper.productoToProductoDto(anuncio.getProductoAsociado()),
                AnuncianteMapper.anuncianteToAnuncianteDto(anuncio.getAnuncianteAsociado()),
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

    public static List<Anuncio> getListAnuncios(List<AnuncioDto> list) {
        List<Anuncio> listaAnuncios = new ArrayList<>();
        for (AnuncioDto anuncioDto : list) {
            Anuncio anuncio = anuncioDtoToAnuncio(anuncioDto);
            listaAnuncios.add(anuncio);
        }
        return listaAnuncios;
    }
}
