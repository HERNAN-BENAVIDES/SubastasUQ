package co.edu.uniquindio.subastasuq.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Usuario extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;

    public Usuario() {

    }

    public Usuario(String nombre, String apellido, String cedula, Integer edad, String username, String password) {
        super(nombre, apellido, cedula, edad);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(getUsername(), usuario.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public String toString() {
        return super.toString() + ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
