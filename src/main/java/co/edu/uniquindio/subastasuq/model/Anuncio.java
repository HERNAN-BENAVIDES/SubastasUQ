package co.edu.uniquindio.subastasuq.model;

import co.edu.uniquindio.subastasuq.mapping.dto.PujaDto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Anuncio implements Serializable{

    private static final long serialVersionUID = 1L;
    private String nombreAnuncio;
    private String codigoAnuncio;
    private LocalDate fechaFinal;
    private LocalTime horaFinal;
    private String descripcionAnuncio;
    private String fotoAnuncio;
    private Producto productoAsociado;
    private Double precioInicial;
    private Double pujaMasAlta;
    private boolean isActivo;
    private String tipoAnuncio;
    private List<Puja> listPujas;

    public Anuncio() {

    }

    public Anuncio(String nombreAnuncio, String codigoAnuncio, LocalDate fechaFinal, LocalTime horaFinal, String descripcionAnuncio, String fotoAnuncio, Producto productoAsociado, Double precioInicial) {
        this.nombreAnuncio = nombreAnuncio;
        this.codigoAnuncio = codigoAnuncio;
        this.fechaFinal = fechaFinal;
        this.horaFinal = horaFinal;
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


    public LocalDate getFechaFinal() {
        return fechaFinal;
    }
    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    public LocalTime getHoraFinal() {
        return horaFinal;
    }
    public void setHoraFinal(LocalTime horaFinal) {
        this.horaFinal = horaFinal;
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


    public int getPujasRealizadasPorUsuario(UsuarioComprador usuarioComprador) {
        return (int) listPujas.stream()
                .filter(puja -> puja.getCompradorAsociado().equals(usuarioComprador))
                .count();
    }

    public void realizarPuja(UsuarioComprador usuarioComprador, Double oferta) {
        Puja puja = new Puja(oferta,usuarioComprador);
        listPujas.add(puja);
    }

    public List<Puja> getPujasUsuario(UsuarioComprador comprador) {
        return listPujas.stream().filter(puja -> puja.getCompradorAsociado().equals(comprador)).toList();
    }
}
