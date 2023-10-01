package co.edu.uniquindio.subastasuq.model;

import java.util.Objects;

public class Producto {

    private String nombre;
    private TipoProducto tipoProducto;
    private String codigo;
    private boolean estado;

    public Producto() {

    }

    public Producto(String nombre, TipoProducto tipoProducto, String codigo, boolean estado) {
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
        this.codigo = codigo;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto producto)) return false;
        return Objects.equals(getNombre(), producto.getNombre()) && getTipoProducto() == producto.getTipoProducto() && Objects.equals(getCodigo(), producto.getCodigo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getTipoProducto(), getCodigo());
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", tipoProducto=" + tipoProducto +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}
