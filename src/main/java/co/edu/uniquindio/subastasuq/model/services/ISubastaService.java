package co.edu.uniquindio.subastasuq.model.services;

import co.edu.uniquindio.subastasuq.excepcions.UsuarioException;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;

public interface ISubastaService {
    public boolean agregarUsuarioAnunciante(UsuarioAnunciante usuario);
    public boolean eliminarUsuarioAnunciante(UsuarioAnunciante usuario) throws UsuarioException;
    public boolean existeUsuarioAnunciante(UsuarioAnunciante usuario);
    public UsuarioAnunciante obtenerUsuarioAnunciante(String username) throws UsuarioException;
    public boolean actualizarUsuarioAnunciante(UsuarioAnunciante usuarioAntiguo, UsuarioAnunciante usuarioActualizado) throws UsuarioException;


}
