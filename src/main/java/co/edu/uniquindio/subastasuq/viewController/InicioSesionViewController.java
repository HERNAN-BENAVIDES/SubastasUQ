package co.edu.uniquindio.subastasuq.viewController;

import co.edu.uniquindio.subastasuq.controller.InicioSesionController;
import co.edu.uniquindio.subastasuq.excepcions.AutenticacionException;
import co.edu.uniquindio.subastasuq.utils.AlertaUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioSesionViewController {

    InicioSesionController inicioSecionService;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button btIniciarSesion;

    @FXML
    private Button btRegistrar;

    @FXML
    private Hyperlink contraseñaOlvidadaHyperlink;

    @FXML
    private RadioButton rbComprador;

    @FXML
    private RadioButton rbAnunciante;

    @FXML
    private ToggleGroup tgUsuario;

    @FXML
    private TextField txtUsuario;

    @FXML
    void initialize() {
        inicioSecionService = new InicioSesionController();
    }



    @FXML
    void iniciarSesionAction(ActionEvent event) {
        iniciarSesion();
    }

    private void iniciarSesion() {
        if (validarCampos()) {
            try {
                if(inicioSecionService.iniciarSesion(txtUsuario.getText(), PasswordField.getText(), determinarTipo())){
                    if(determinarTipo().equals("Anunciante")){
                        abrirVentanaAnunciante();
                    }else if(determinarTipo().equals("Comprador")){
                        abrirVentanaComprador();
                    }
                }
            } catch (AutenticacionException | IOException e) {
                AlertaUtils.mostrarAlertaError(e.getMessage());
            }

        }
    }

    private void abrirVentanaComprador() {
        try {
            Stage ventanaActual = (Stage) btIniciarSesion.getScene().getWindow(); // Reemplaza yourButton con el control que desencadena la acción.
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/subastasuq/comprador/CompradorView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(scene);
            ventanaActual.close();
            // Mostrar la nueva ventana
            nuevaVentana.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirVentanaAnunciante() {
        try {
            Stage ventanaActual = (Stage) btIniciarSesion.getScene().getWindow();
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/subastasuq/vendedor/AnuncianteView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(scene);
            ventanaActual.close();
            // Mostrar la nueva ventana
            nuevaVentana.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String determinarTipo() {
        if(rbComprador.isSelected()){
            return "Comprador";
        } else if (rbAnunciante.isSelected()) {
            return "Anunciante";
        }
        return "";
    }

    private boolean validarCampos() {
        if (rbComprador == null && rbAnunciante == null) {
            AlertaUtils.mostrarAlertaError("Seleccione un tipo de usuario");
            return false;
        }

        if (txtUsuario.getText().isEmpty() || PasswordField.getText().isEmpty()) {
            AlertaUtils.mostrarAlertaError("Debe completar todos los campos");
            return false;
        }
        return true;
    }


    @FXML
    void registrarAction(ActionEvent event) {

    }

}
