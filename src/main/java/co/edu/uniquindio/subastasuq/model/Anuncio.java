package co.edu.uniquindio.subastasuq.model;

import java.io.Serializable;
import java.util.Objects;

public class Anuncio implements Serializable{

    private static final long serialVersionUID = 1L;
    private String nombreAnuncio;
    private String codigoAnuncio;
    private String descripcionAnuncio;
    private String fotoAnuncio;
    private Producto productoAsociado;
    private Double precioInicial;
    private Double pujaMasAlta;

    public Anuncio() {

    }

    public Anuncio(String nombreAnuncio, String codigoAnuncio, String descripcionAnuncio, String fotoAnuncio, Producto productoAsociado, Double precioInicial) {
        this.nombreAnuncio = nombreAnuncio;
        this.codigoAnuncio = codigoAnuncio;
        this.descripcionAnuncio = descripcionAnuncio;
        this.fotoAnuncio = fotoAnuncio;
        this.productoAsociado = productoAsociado;
        this.precioInicial = precioInicial;
        this.pujaMasAlta = precioInicial;
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



}
