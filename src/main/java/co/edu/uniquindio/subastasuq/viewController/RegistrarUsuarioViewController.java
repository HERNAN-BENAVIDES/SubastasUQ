package co.edu.uniquindio.subastasuq.viewController;

import co.edu.uniquindio.subastasuq.controller.ModelFactoryController;
import co.edu.uniquindio.subastasuq.controller.RegistrarUsuarioController;
import co.edu.uniquindio.subastasuq.excepcions.NuevoAnuncianteExcepcion;
import co.edu.uniquindio.subastasuq.excepcions.NuevoCompradorException;
import co.edu.uniquindio.subastasuq.mapping.dto.UsuarioAnuncianteDto;
import co.edu.uniquindio.subastasuq.mapping.dto.UsuarioCompradorDto;
import co.edu.uniquindio.subastasuq.mapping.mappers.AnuncianteMapper;
import co.edu.uniquindio.subastasuq.mapping.mappers.CompradorMapper;
import co.edu.uniquindio.subastasuq.model.UsuarioComprador;
import co.edu.uniquindio.subastasuq.utils.AlertaUtils;
import co.edu.uniquindio.subastasuq.utils.Persistencia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class RegistrarUsuarioViewController {

    @FXML
    private Button btAtras;

    @FXML
    private Button btRegistrar;

    @FXML
    private RadioButton rbAnunciante;

    @FXML
    private RadioButton rbComprador;

    @FXML
    private ToggleGroup tgUsuario;

    @FXML
    private TextField txApellido;

    @FXML
    private TextField txCedula;

    @FXML
    private TextField txEdad;

    @FXML
    private TextField txNombre;

    @FXML
    private TextField txPassword;

    @FXML
    private TextField txUsername;
    RegistrarUsuarioController registroUsuarioService;

    @FXML
    void initialize(){
        registroUsuarioService = new RegistrarUsuarioController();
        inicializarCampos();
    }

    private void inicializarCampos() {

        txNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                txNombre.setText(oldValue);
            }
        });

        txApellido.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                txApellido.setText(oldValue);
            }
        });

        txCedula.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txCedula.setText(oldValue);
            }
        });

        txEdad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txEdad.setText(oldValue);
            }
        });
    }

    @FXML
    void atras(ActionEvent event) {
        volverVentanaAnterior();
    }

    private void volverVentanaAnterior() {
        try {
            Stage ventanaActual = (Stage) btAtras.getScene().getWindow();

            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/subastasuq/InicioSesion.fxml"));
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

    private void manejarCierre(WindowEvent windowEvent, Stage stage) {
        if(AlertaUtils.mostrarAlertaConfirmacion("¿Desea cerrar la aplicación?")){
            registroUsuarioService.guardarCambios();
            stage.close();
        }else{
            windowEvent.consume();
        }
    }

    @FXML
    void registrarNuevoUser(ActionEvent event) {
        crearNuevoUsuario();
    }

    private void crearNuevoUsuario() {
        if(validarCampos()){
            if(rbComprador.isSelected()){
                UsuarioCompradorDto compradorDto = crearComprador();
                try {
                    if(registroUsuarioService.agregarNuevoComprador(compradorDto)){
                        Persistencia.guardarComprador(CompradorMapper.compradorDtoToComprador(compradorDto));
                        AlertaUtils.mostrarAlertaInformacion("El registro fue exitoso");
                        abrirVentanaComprador();
                    }
                } catch (NuevoCompradorException e) {
                    AlertaUtils.mostrarAlertaInformacion(e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (rbAnunciante.isSelected()) {
                UsuarioAnuncianteDto anuncianteDto = crearAnunciante();
                try {
                    if(registroUsuarioService.agregarNuevoAnunciante(anuncianteDto)){
                        Persistencia.guardarAnunciante(AnuncianteMapper.anuncianteDtoToAnunciante(anuncianteDto));
                        AlertaUtils.mostrarAlertaInformacion("El registro fue exitoso");
                        abrirVentanaAnunciante();
                    }
                } catch (NuevoAnuncianteExcepcion e) {
                    AlertaUtils.mostrarAlertaError(e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void abrirVentanaAnunciante() {
        try {
            Stage ventanaActual = (Stage) btRegistrar.getScene().getWindow();

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

    private void abrirVentanaComprador() {
        try {
            Stage ventanaActual = (Stage) btRegistrar.getScene().getWindow(); // Reemplaza yourButton con el control que desencadena la acción.

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

    private UsuarioAnuncianteDto crearAnunciante() {
        return new UsuarioAnuncianteDto(txNombre.getText(),txApellido.getText(),txCedula.getText(),
                Integer.parseInt(txEdad.getText()),txUsername.getText(),txPassword.getText());
    }

    private UsuarioCompradorDto crearComprador() {
        return new UsuarioCompradorDto(txNombre.getText(),txApellido.getText(),txCedula.getText(),
                Integer.parseInt(txEdad.getText()),txUsername.getText(),txPassword.getText());
    }

    private boolean validarCampos() {
        String nombre = txNombre.getText().trim();
        String apellido = txApellido.getText().trim();
        String cedula = txCedula.getText().trim();
        String edad = txEdad.getText().trim();
        String username = txUsername.getText().trim();
        String password = txPassword.getText().trim();

        // Verificar que al menos un RadioButton esté seleccionado
        if (!(rbAnunciante.isSelected() || rbComprador.isSelected())) {
            AlertaUtils.mostrarAlertaError("Por favor, seleccione el tipo de usuario.");
            return false;
        }

        if (nombre.isEmpty() || apellido.isEmpty() || cedula.isEmpty() || edad.isEmpty() || username.isEmpty()
                || password.isEmpty()) {
            AlertaUtils.mostrarAlertaError("Por favor, complete todos los campos.");
            return false;
        }
        return true;
    }

}