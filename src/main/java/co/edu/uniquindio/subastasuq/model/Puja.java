package co.edu.uniquindio.subastasuq.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Puja implements Serializable {

    private static final long serialVersionUID = 1L;
    private LocalDateTime fecha;
    private Double oferta;
    private UsuarioComprador compradorAsociado;
    private Boolean isAceptada;

    public Puja() {

    }

    public Puja(Double oferta, UsuarioComprador compradorAsociado) {
        this.fecha = LocalDateTime.now();
        this.oferta = oferta;
        this.compradorAsociado = compradorAsociado;
        this.isAceptada = false;


    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getOferta() {
        return oferta;
    }

    public void setOferta(Double oferta) {
        this.oferta = oferta;
    }

    public UsuarioComprador getCompradorAsociado() {
        return compradorAsociado;
    }

    public void setCompradorAsociado(UsuarioComprador compradorAsociado) {
        this.compradorAsociado = compradorAsociado;
    }

    public Boolean getAceptada() {
        return isAceptada;
    }

    public void setAceptada(Boolean aceptada) {
        isAceptada = aceptada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Puja puja)) return false;
        return Objects.equals(getFecha(), puja.getFecha()) && Objects.equals(getOferta(), puja.getOferta()) && Objects.equals(getCompradorAsociado(), puja.getCompradorAsociado()) && Objects.equals(isAceptada, puja.isAceptada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFecha(), getOferta(), getCompradorAsociado(), isAceptada);
    }

    public void actualizarPrecioAnuncio(Double oferta, Anuncio anuncio){
        if(oferta > anuncio.getPujaMasAlta()){
            anuncio.setPujaMasAlta(oferta);
        }
    }


}
