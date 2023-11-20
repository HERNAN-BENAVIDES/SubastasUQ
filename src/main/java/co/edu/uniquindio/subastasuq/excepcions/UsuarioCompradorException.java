package co.edu.uniquindio.subastasuq.excepcions;

public class UsuarioCompradorException extends Throwable {
    public UsuarioCompradorException(String usuarioNoEncontrado) {
        super(usuarioNoEncontrado);
    }
}
