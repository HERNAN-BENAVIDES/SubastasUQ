package co.edu.uniquindio.subastasuq.viewController;

import co.edu.uniquindio.subastasuq.controller.InicioSesionController;
import co.edu.uniquindio.subastasuq.controller.ProductoController;
import co.edu.uniquindio.subastasuq.excepcions.UsuarioException;
import co.edu.uniquindio.subastasuq.utils.AlertaUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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

                }
            } catch (UsuarioException | IOException e) {
                AlertaUtils.mostrarAlertaError(e.getMessage());
            }

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
