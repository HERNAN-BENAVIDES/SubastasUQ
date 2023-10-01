package co.edu.uniquindio.subastasuq.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductoViewController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private ComboBox<?> cbxEstado;

    @FXML
    private ComboBox<?> cbxTipo;

    @FXML
    private TableView<?> tableProductos;

    @FXML
    private TableColumn<?, ?> tcCodigo;

    @FXML
    private TableColumn<?, ?> tcEstado;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TableColumn<?, ?> tcTipo;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    void actualizarProductoAction(ActionEvent event) {

    }

    @FXML
    void agregarProductoAction(ActionEvent event) {

    }

    @FXML
    void eliminarProductoAction(ActionEvent event) {

    }

    @FXML
    void nuevoProductoAction(ActionEvent event) {

    }

}
