package co.edu.uniquindio.subastasuq.model;

import java.util.Objects;

public class Anuncio {

    private String nombreAnuncio;
    private String codigoAnuncio;
    private String descripcionAnuncio;
    private String fotoAnuncio;
    private Producto productoAsociado;

    public Anuncio() {

    }

    public Anuncio(String nombreAnuncio, String codigoAnuncio, String descripcionAnuncio, String fotoAnuncio, Producto productoAsociado) {
        this.nombreAnuncio = nombreAnuncio;
        this.codigoAnuncio = codigoAnuncio;
        this.descripcionAnuncio = descripcionAnuncio;
        this.fotoAnuncio = fotoAnuncio;
        this.productoAsociado = productoAsociado;
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
