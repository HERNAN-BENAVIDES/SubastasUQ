package co.edu.uniquindio.subastasuq.model;

import java.time.LocalDateTime;

public class Puja {

    private String codigo;
    private LocalDateTime fecha = LocalDateTime.now();
    private Anuncio anuncioAsociado;
    private Double oferta;

    public Puja(String codigo, Double oferta, Anuncio anuncioAsociado) {
        this.codigo = codigo;
        this.oferta = oferta;
        this.anuncioAsociado = anuncioAsociado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
