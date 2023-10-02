package co.edu.uniquindio.subastasuq.viewController;

import co.edu.uniquindio.subastasuq.controller.ProductoController;
import co.edu.uniquindio.subastasuq.excepcions.ProductoException;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.model.TipoProducto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoViewController {

    ProductoController productoControllerService;
    ObservableList<ProductoDto> listaProductosDto = FXCollections.observableArrayList();
    ProductoDto productoSeleccionado;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private ComboBox<String> cbxEstado;

    @FXML
    private ComboBox<String> cbxTipo;

    @FXML
    private TableView<ProductoDto> tableProductos;

    @FXML
    private TableColumn<ProductoDto, String> tcCodigo;

    @FXML
    private TableColumn<ProductoDto, String> tcEstado;

    @FXML
    private TableColumn<ProductoDto, String> tcNombre;

    @FXML
    private TableColumn<ProductoDto, String> tcTipo;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;


    @FXML
    void initialize() {
        productoControllerService = new ProductoController();
        intiView();
    }

    @FXML
    void nuevoProductoAction(ActionEvent event) {
        limpiarCamposProducto();
    }

    @FXML
    void actualizarProductoAction(ActionEvent event) throws ProductoException {
        actualizarProducto();
    }

    @FXML
    void eliminarProductoAction(ActionEvent event) throws ProductoException {
        eliminarProducto();
    }

    @FXML
    void agregarProductoAction(ActionEvent event){
        crearProducto();
    }




    private void intiView() {
        initDataBinding();
        obtenerProductos();
        inicializarCampos();
        tableProductos.getItems().clear();
        tableProductos.setItems(listaProductosDto);
        listenerSelection();
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigo()));
        tcTipo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().tipoProducto())));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estado()));
    }

   private void obtenerProductos() {
        listaProductosDto.addAll(productoControllerService.obtenerProductos());
   }

    private void listenerSelection() {
        tableProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            productoSeleccionado = newSelection;
            mostrarInformacionEmpleado(productoSeleccionado);
        });
    }

    private void mostrarInformacionEmpleado(ProductoDto productoSeleccionado) {
        if (productoSeleccionado != null) {
            txtNombre.setText(productoSeleccionado.nombre());
            txtCodigo.setText(productoSeleccionado.codigo());
            cbxEstado.getSelectionModel().select(productoSeleccionado.estado());
            cbxTipo.getSelectionModel().select(String.valueOf(productoSeleccionado.tipoProducto()));
        }
    }


    private void crearProducto(){
        if(validarCampos()){
            ProductoDto productoDto = crearProductoDto();
            if (productoControllerService.agregarProducto(productoDto)) {
                mostrarAlertaInformacion("El producto se agregó correctamente");
                tableProductos.getItems().clear();
                obtenerProductos();
            }
            limpiarCamposProducto();

        }
    }

    private void limpiarCamposProducto() {
        txtNombre.clear();
        txtCodigo.clear();
        cbxTipo.getSelectionModel().clearSelection();
        cbxEstado.getSelectionModel().clearSelection();
    }


    private ProductoDto crearProductoDto() {
       return new ProductoDto(txtNombre.getText(),
               TipoProducto.valueOf(cbxTipo.getSelectionModel().getSelectedItem().toUpperCase()),
               txtCodigo.getText(),
               String.valueOf(cbxEstado.getSelectionModel().getSelectedItem()));
    }

    private boolean validarCampos() {
        String nombre = txtNombre.getText();
        String codigo = txtCodigo.getText();
        String estado = cbxEstado.getSelectionModel().getSelectedItem();
        String tipoProducto = cbxTipo.getSelectionModel().getSelectedItem();

        // Verifica si alguno de los campos es nulo o vacío
        if (nombre.isEmpty() || codigo.isEmpty() || estado == null || tipoProducto == null) {
            mostrarAlertaInformacion("Por favor complete todos los campos");
            return false; // Retorna false si los campos no están completos
        } else {
            return true; // Retorna true si todos los campos están completos
        }
    }

    private void actualizarProducto() throws ProductoException {
        if(productoSeleccionado != null){
            if(validarCampos()){
                ProductoDto productoNuevo = crearProductoDto();
                if(!productoNuevo.equals(productoSeleccionado)) {
                    if (productoControllerService.actualizarProducto(productoSeleccionado, productoNuevo)) {
                        mostrarAlertaInformacion("Se Actualizo correctamente el producto");
                        tableProductos.getItems().clear();
                        obtenerProductos();
                        limpiarCamposProducto();
                    }
                }else{
                    mostrarAlertaInformacion("El producto no subrio ningun cambio");
                }
            }
        }else{
            mostrarAlertaInformacion("Seleccione un producto");
        }
    }


    private void eliminarProducto() throws ProductoException {
        if(productoSeleccionado != null){
            if (mostrarAlertaConfirmacion("¿Esta seguro que desea eliminar el producto seleccionado?")){
                if(productoControllerService.eliminarProducto(productoSeleccionado)){
                    mostrarAlertaInformacion("Se elimino correctamente el producto");
                    tableProductos.getItems().clear();
                    obtenerProductos();
                    limpiarCamposProducto();
                }
            }
        }else{
            mostrarAlertaInformacion("Seleccione un producto");
        }
    }



    private void inicializarCampos() {
        txtCodigo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCodigo.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                txtNombre.setText(newValue.replaceAll("[^a-zA-Z]", ""));
            }
        });

        // Crear una lista de los tipos de productos
        List<String> tiposProductos = Arrays.asList("Tecnologia", "Hogar", "Deporte", "Vehiculo", "Bienraiz");
        ObservableList<String> tiposProductosList = FXCollections.observableArrayList(tiposProductos);
        cbxTipo.setItems(tiposProductosList);

        // Crear una lista de los estados de productos
        List<String> estadosProductos = Arrays.asList("Nuevo", "Usado");
        ObservableList<String> estadosProductosList = FXCollections.observableArrayList(estadosProductos);
        cbxEstado.setItems(estadosProductosList);


    }


    private void mostrarAlertaInformacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


    private boolean mostrarAlertaConfirmacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }




}
