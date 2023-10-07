package co.edu.uniquindio.subastasuq.excepcions;

public class AutenticacionException extends Throwable {
    public AutenticacionException(String credencialesDeAnuncianteIncorrectas) {
        super(credencialesDeAnuncianteIncorrectas);
    }
}
