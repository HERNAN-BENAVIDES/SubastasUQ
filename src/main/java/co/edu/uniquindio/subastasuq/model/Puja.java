package co.edu.uniquindio.subastasuq.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Puja implements Serializable {

    private static final long serialVersionUID = 1L;
    private LocalDateTime fecha = LocalDateTime.now();
    private Anuncio anuncioAsociado;
    private Double oferta;

    public Puja() {

    }

    public Puja(Double oferta, Anuncio anuncioAsociado) {
        this.oferta = oferta;
        this.anuncioAsociado = anuncioAsociado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Anuncio getAnuncioAsociado() {
        return anuncioAsociado;
    }

    public void setAnuncioAsociado(Anuncio anuncioAsociado) {
        this.anuncioAsociado = anuncioAsociado;
    }

    public Double getOferta() {
        return oferta;
    }

    public void setOferta(Double oferta) {
        this.oferta = oferta;
    }
}
