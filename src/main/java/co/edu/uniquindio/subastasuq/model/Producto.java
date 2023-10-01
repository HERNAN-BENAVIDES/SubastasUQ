package co.edu.uniquindio.subastasuq.model;

public class Producto {

    private String nombre;
    private TipoProducto tipoProducto;

    public Producto() {

    }

    public Producto(String nombre, TipoProducto tipoProducto) {
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
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

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", tipoProducto=" + tipoProducto +
                '}';
    }
}
