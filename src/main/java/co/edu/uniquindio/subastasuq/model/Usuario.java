package co.edu.uniquindio.subastasuq.model;

public abstract class Usuario extends Persona{
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
}
