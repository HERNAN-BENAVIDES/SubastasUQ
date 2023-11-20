package co.edu.uniquindio.subastasuq.model;

import java.io.Serializable;
import java.util.Objects;

public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private String tipoProducto;
    private String codigo;
    private String estado;
    private UsuarioAnunciante anuncianteAsociado;

    public Producto() {

    }

    public Producto(String nombre, String tipoProducto, String codigo, String estado, UsuarioAnunciante anuncianteAsociado) {
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
        this.codigo = codigo;
        this.estado = estado;
        this.anuncianteAsociado = anuncianteAsociado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto producto)) return false;
        return Objects.equals(getNombre(), producto.getNombre()) && getTipoProducto() == producto.getTipoProducto() && Objects.equals(getCodigo(), producto.getCodigo()) && Objects.equals(estado, producto.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getTipoProducto(), getCodigo(), estado);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", tipoProducto=" + tipoProducto +
                ", codigo='" + codigo + '\'' +
                ", estado=" + estado +
                '}';
    }

    public UsuarioAnunciante getAnuncianteAsociado() {
        return anuncianteAsociado;
    }

    public void setAnuncianteAsociado(UsuarioAnunciante anuncianteAsociado) {
        this.anuncianteAsociado = anuncianteAsociado;
    }
}
