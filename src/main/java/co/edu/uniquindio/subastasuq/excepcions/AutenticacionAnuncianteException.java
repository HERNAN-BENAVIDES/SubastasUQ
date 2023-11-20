package co.edu.uniquindio.subastasuq.excepcions;

public class AutenticacionAnuncianteException extends Throwable {
    public AutenticacionAnuncianteException(String credencialesDeAnuncianteIncorrectas) {
        super(credencialesDeAnuncianteIncorrectas);
    }
}
