package co.edu.uniquindio.subastasuq.excepcions;

public class AutenticacionCompradorException extends Throwable {
    public AutenticacionCompradorException(String credencialesDeCompradorIncorrectas) {
        super(credencialesDeCompradorIncorrectas);
    }
}
