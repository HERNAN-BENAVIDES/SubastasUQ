package co.edu.uniquindio.subastasuq.model;

import java.util.ArrayList;
import java.util.List;

public class UsuarioComprador extends Usuario{

    private List<Puja> listPujas = new ArrayList<Puja>();

    public UsuarioComprador() {

    }

    public UsuarioComprador(String nombre, String apellido, String cedula, Integer edad, String username, String password) {
        super(nombre, apellido, cedula, edad, username, password);
    }

    public List<Puja> getListPujas() {
        return listPujas;
    }

    public void setListPujas(List<Puja> listPujas) {
        this.listPujas = listPujas;
    }
}
