package co.edu.uniquindio.subastasuq.model;

import co.edu.uniquindio.subastasuq.excepcions.UsuarioException;
import co.edu.uniquindio.subastasuq.model.services.ISubastaService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Subasta implements ISubastaService, Serializable {

    private static final long serialVersionUID = 1L;
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

    public boolean agregarUsuarioAnunciante(UsuarioAnunciante usuario){
        return  getListAnunciantes().add(usuario);
    }

    public boolean eliminarUsuarioAnunciante(UsuarioAnunciante usuario) throws UsuarioException {
        if (listAnunciantes.contains(usuario)) {
            listAnunciantes.remove(usuario);
            return true;
        } else {
            throw new UsuarioException("El Usuario no se encuentra registrado");
        }
    }

    public boolean existeUsuarioAnunciante(UsuarioAnunciante usuario) {
        return listAnunciantes.contains(usuario);
    }

    public UsuarioAnunciante obtenerAnunciante(String username) throws UsuarioException {
        for (UsuarioAnunciante usuario : listAnunciantes) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        throw new UsuarioException("Usuario no encontrado");
    }

    public boolean actualizarUsuarioAnunciante(UsuarioAnunciante usuarioAntiguo, UsuarioAnunciante usuarioActualizado) throws UsuarioException {
        if (usuarioAntiguo == null) {
            throw new UsuarioException("El Usuario seleccionado es nulo");
        }

        int i = listAnunciantes.indexOf(usuarioAntiguo);

        if (i != -1) {
            listAnunciantes.set(i, usuarioActualizado);
            return true;
        } else {
            throw new UsuarioException("El Usuario no existe");
        }
    }

    @Override
    public List<Anuncio> getListAnuncios() {
        List<Anuncio> anunciosActivos = new ArrayList<>();

        for (UsuarioAnunciante anunciante : listAnunciantes) {
            for (Anuncio anuncio : anunciante.getListAnuncios()) {
                if (anuncio.getIsActivo()) {
                    anunciosActivos.add(anuncio);
                }
            }
        }
        return anunciosActivos;
    }

    @Override
    public UsuarioComprador obtenerComprador(String user) throws UsuarioException {
        for (UsuarioComprador comprador : listCompradores) {
            if (comprador.getUsername().equals(user)) {
                return comprador;
            }
        }
        throw new UsuarioException("Usuario no encontrado");
    }


}
