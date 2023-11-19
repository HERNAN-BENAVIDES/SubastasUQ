package co.edu.uniquindio.subastasuq.model;

import co.edu.uniquindio.subastasuq.excepcions.PujaException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsuarioComprador extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Puja> listPujas;

    public UsuarioComprador() {

    }

    public UsuarioComprador(String nombre, String apellido, String cedula, Integer edad, String username, String password) {
        super(nombre, apellido, cedula, edad, username, password);
        this.listPujas = new ArrayList<Puja>();
    }

    public List<Puja> getListPujas() {
        return listPujas;
    }

    public void setListPujas(List<Puja> listPujas) {
        this.listPujas = listPujas;
    }

    public boolean realizarPuja(Anuncio anuncio, UsuarioComprador comprador) throws PujaException {
        int pujasRealizadas = anuncio.getPujasRealizadasPorUsuario(comprador);
        if (pujasRealizadas < 3) {
            return true;
        } else {
            throw new PujaException("Numero máximo de pujas por este anuncio alcanzado");
        }
    }
}
