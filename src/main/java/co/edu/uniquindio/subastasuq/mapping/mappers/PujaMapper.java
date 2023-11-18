package co.edu.uniquindio.subastasuq.mapping.mappers;

import co.edu.uniquindio.subastasuq.mapping.dto.PujaDto;
import co.edu.uniquindio.subastasuq.model.Puja;

import java.util.ArrayList;
import java.util.List;

public class PujaMapper {

    public static List<PujaDto> getListPujas(List<Puja> listPujas) {
        List<PujaDto> pujaDtoList = new ArrayList<>();
        for (Puja puja: listPujas) {
            PujaDto pujaDto = pujaToPujaDto(puja);
            pujaDtoList.add(pujaDto);
        }
        return pujaDtoList;
    }

    public static PujaDto pujaToPujaDto(Puja puja) {
        return new PujaDto(puja.getFecha(), puja.getOferta(), CompradorMapper.compradorToCompradorDto(puja.getCompradorAsociado()),
                puja.getAceptada());
    }

    public static List<Puja> setListPujas(List<PujaDto> pujaDtos) {
        List<Puja> pujaList = new ArrayList<>();
        for (PujaDto pujaDto: pujaDtos) {
            Puja puja = pujaDtoToPuja(pujaDto);
            pujaList.add(puja);
        }
        return pujaList;
    }

    public static Puja pujaDtoToPuja(PujaDto pujaDto) {
        return new Puja(pujaDto.oferta(), CompradorMapper.compradorDtoToComprador(pujaDto.compradorAsociado()));//,AnuncioMapper.anuncioDtoToAnuncio(pujaDto.anuncioAsociado()));
    }
}
