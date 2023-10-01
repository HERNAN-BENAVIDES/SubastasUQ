package co.edu.uniquindio.subastasuq.model;

import co.edu.uniquindio.subastasuq.model.services.ISubastaService;

import java.util.ArrayList;
import java.util.List;

public class Subasta implements ISubastaService {

    private List<UsuarioAnunciante> listAnunciantes = new ArrayList<UsuarioAnunciante>();
    private List<UsuarioComprador> listCompradores = new ArrayList<UsuarioComprador>();

    public Subasta() {

    }

    public List<UsuarioAnunciante> getListAnunciantes() {
        return listAnunciantes;
    }

    public void setListAnunciantes(List<UsuarioAnunciante> listAnunciantes) {
        this.listAnunciantes = listAnunciantes;
    }

    public List<UsuarioComprador> getListCompradores() {
        return listCompradores;
    }

    public void setListCompradores(List<UsuarioComprador> listCompradores) {
        this.listCompradores = listCompradores;
    }
}
