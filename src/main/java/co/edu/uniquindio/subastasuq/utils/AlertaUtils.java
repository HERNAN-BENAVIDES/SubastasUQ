package co.edu.uniquindio.subastasuq.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertaUtils {
    /**
     * Muestra una alerta de información en la interfaz gráfica.
     * @param mensaje Mensaje de la alerta informativa.
     */
    public static void mostrarAlertaInformacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Muestra una alerta de error en la interfaz gráfica.
     * @param mensaje Mensaje de la alerta de error.
     */
    public static void mostrarAlertaError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Muestra una alerta de confirmación en la interfaz gráfica.
     * @param mensaje Mensaje de la alerta de confirmación.
     * @return true si el usuario confirma la acción, false en caso contrario.
     */
    public static boolean mostrarAlertaConfirmacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

}
