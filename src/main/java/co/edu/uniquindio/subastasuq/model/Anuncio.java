package co.edu.uniquindio.subastasuq.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Anuncio implements Serializable{

    private static final long serialVersionUID = 1L;
    private String nombreAnuncio;
    private String codigoAnuncio;
    private LocalDateTime fechaFinal;
    private String descripcionAnuncio;
    private String fotoAnuncio;
    private Producto productoAsociado;
    private Double precioInicial;
    private Double pujaMasAlta;
    private Boolean isActivo;
    private String tipoAnuncio;
    private List<Puja> listPujas;

    public Anuncio() {

    }

    public Anuncio(String nombreAnuncio, String codigoAnuncio, LocalDateTime fechaFinal, String descripcionAnuncio, String fotoAnuncio, Producto productoAsociado, Double precioInicial) {
        this.nombreAnuncio = nombreAnuncio;
        this.codigoAnuncio = codigoAnuncio;
        this.fechaFinal = fechaFinal;
        this.descripcionAnuncio = descripcionAnuncio;
        this.fotoAnuncio = fotoAnuncio;
        this.productoAsociado = productoAsociado;
        this.tipoAnuncio = productoAsociado.getTipoProducto();
        this.precioInicial = precioInicial;
        this.pujaMasAlta = precioInicial;
        this.isActivo = true;
        this.listPujas = new ArrayList<>();
    }

    public String getNombreAnuncio() {
        return nombreAnuncio;
    }

    public void setNombreAnuncio(String nombreAnuncio) {
        this.nombreAnuncio = nombreAnuncio;
    }

    public String getCodigoAnuncio() {
        return codigoAnuncio;
    }

    public void setCodigoAnuncio(String codigoAnuncio) {
        this.codigoAnuncio = codigoAnuncio;
    }

    public String getDescripcionAnuncio() {
        return descripcionAnuncio;
    }

    public void setDescripcionAnuncio(String descripcionAnuncio) {
        this.descripcionAnuncio = descripcionAnuncio;
    }

    public String getFotoAnuncio() {
        return fotoAnuncio;
    }

    public void setFotoAnuncio(String fotoAnuncio) {
        this.fotoAnuncio = fotoAnuncio;
    }

    public Producto getProductoAsociado() {
        return productoAsociado;
    }

    public void setProductoAsociado(Producto productoAsociado) {
        this.productoAsociado = productoAsociado;
    }

    public Double getPrecioInicial() {
        return precioInicial;
    }

    public void setPrecioInicial(Double precioInicial) {
        this.precioInicial = precioInicial;
    }

    public Double getPujaMasAlta() {
        return pujaMasAlta;
    }

    public void setPujaMasAlta(Double pujaMasAlta) {
        this.pujaMasAlta = pujaMasAlta;
    }

    public boolean getIsActivo() {
        return isActivo;
    }

    public void setIsActivo(boolean activo) {
        this.isActivo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Anuncio anuncio)) return false;
        return Objects.equals(getNombreAnuncio(), anuncio.getNombreAnuncio()) && Objects.equals(getCodigoAnuncio(), anuncio.getCodigoAnuncio()) && Objects.equals(getProductoAsociado(), anuncio.getProductoAsociado());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombreAnuncio(), getCodigoAnuncio(), getProductoAsociado());
    }


    public LocalDateTime getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDateTime fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public List<Puja> getListPujas() {
        return listPujas;
    }

    public void setListPujas(List<Puja> listPujas) {
        this.listPujas = listPujas;
    }


    public String getTipoAnuncio() {
        return tipoAnuncio;
    }

    public void setTipoAnuncio(String tipoAnuncio) {
        this.tipoAnuncio = tipoAnuncio;
    }
}
