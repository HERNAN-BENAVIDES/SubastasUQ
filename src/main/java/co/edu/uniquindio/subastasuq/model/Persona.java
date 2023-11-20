package co.edu.uniquindio.subastasuq.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private String apellido;
    private String cedula;
    private Integer edad;

    public Persona() {

    }

    public Persona(String nombre, String apellido, String cedula, Integer edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona persona)) return false;
        return Objects.equals(getNombre(), persona.getNombre()) && Objects.equals(getApellido(), persona.getApellido()) && Objects.equals(getCedula(), persona.getCedula());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getApellido(), getCedula());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cedula='" + cedula + '\'' +
                ", edad=" + edad + '\'';
    }
}
