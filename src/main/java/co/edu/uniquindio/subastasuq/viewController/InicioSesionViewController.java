package co.edu.uniquindio.subastasuq.viewController;

import co.edu.uniquindio.subastasuq.controller.InicioSesionController;
import co.edu.uniquindio.subastasuq.excepcions.AutenticacionAnuncianteException;
import co.edu.uniquindio.subastasuq.excepcions.AutenticacionCompradorException;
import co.edu.uniquindio.subastasuq.excepcions.AutenticacionException;
import co.edu.uniquindio.subastasuq.utils.AlertaUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
            } catch (AutenticacionException | IOException | AutenticacionCompradorException |
                     AutenticacionAnuncianteException e) {
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

            nuevaVentana.setOnCloseRequest(windowEvent -> manejarCierre(windowEvent, nuevaVentana));

            nuevaVentana.setX(ventanaActual.getX());
            nuevaVentana.setY(ventanaActual.getY());
            nuevaVentana.setWidth(ventanaActual.getWidth());
            nuevaVentana.setHeight(ventanaActual.getHeight());

            // Mostrar la nueva ventana
            nuevaVentana.show();
            ventanaActual.close();

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

            nuevaVentana.setOnCloseRequest(windowEvent -> manejarCierre(windowEvent, nuevaVentana));

            // Configurar la nueva ventana en la misma posición y con el mismo tamaño
            nuevaVentana.setX(ventanaActual.getX());
            nuevaVentana.setY(ventanaActual.getY());
            nuevaVentana.setWidth(ventanaActual.getWidth());
            nuevaVentana.setHeight(ventanaActual.getHeight());

            // Mostrar la nueva ventana
            nuevaVentana.show();
            ventanaActual.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void manejarCierre(WindowEvent windowEvent, Stage stage) {
        if(AlertaUtils.mostrarAlertaConfirmacion("¿Desea cerrar la aplicación?")){
            inicioSecionService.guardarCambios();
            stage.close();
        }else{
            windowEvent.consume();
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
        abrirVentanaRegistrarNuevoUsuario();
    }

    private void abrirVentanaRegistrarNuevoUsuario() {
        try {
            Stage ventanaActual = (Stage) btRegistrar.getScene().getWindow();

            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/subastasuq/RegistrarNuevoUsuario.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(scene);

            nuevaVentana.setOnCloseRequest(windowEvent -> manejarCierre(windowEvent, nuevaVentana));

            // Configurar la nueva ventana en la misma posición y con el mismo tamaño
            nuevaVentana.setX(ventanaActual.getX());
            nuevaVentana.setY(ventanaActual.getY());
            nuevaVentana.setWidth(ventanaActual.getWidth());
            nuevaVentana.setHeight(ventanaActual.getHeight());

            // Mostrar la nueva ventana
            nuevaVentana.show();
            ventanaActual.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
