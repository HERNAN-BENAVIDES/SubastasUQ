package co.edu.uniquindio.subastasuq.excepcions;

public class UsuarioAnuncianteException extends Throwable {
    public UsuarioAnuncianteException(String usuarioNoEncontrado) {
        super(usuarioNoEncontrado);
    }
}
