package co.edu.uniquindio.subastasuq.viewController;

import co.edu.uniquindio.subastasuq.controller.ProductoController;
import co.edu.uniquindio.subastasuq.excepcions.ProductoException;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.utils.AlertaUtils;
import co.edu.uniquindio.subastasuq.utils.Persistencia;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Arrays;
import java.util.List;

public class ProductoViewController {

    ProductoController productoControllerService;
    ObservableList<ProductoDto> listaProductosDto = FXCollections.observableArrayList();
    ProductoDto productoSeleccionado;

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
    private TextArea txtInformation;



    /**
     * Método de inicialización de la vista.
     * Este método se utiliza para configurar componentes de la interfaz gráfica de usuario (GUI) en JavaFX.
     * Se llama automáticamente al cargar la vista.
     */
    @FXML
    void initialize() {
        productoControllerService = new ProductoController();
        intiView();
    }

    /**
     * Acción para crear un nuevo producto.
     * Este método se llama cuando se realiza una acción en un elemento de la interfaz gráfica relacionado con la creación de un nuevo producto.
     * Su función principal es limpiar los campos del formulario.
     * @param event Objeto ActionEvent que representa el evento que desencadenó la acción.
     */
    @FXML
    void limpiarCamposAction(ActionEvent event) {
        limpiarCamposProducto();
    }

    /**
     * Acción para actualizar un producto.
     * Este método se llama cuando se realiza una acción en un elemento de la interfaz gráfica relacionado con la actualización de un producto.
     * Su función principal es llamar al método actualizarProducto().
     * @param event Objeto ActionEvent que representa el evento que desencadenó la acción.
     */
    @FXML
    void actualizarProductoAction(ActionEvent event){
        actualizarProducto();
    }

    /**
     * Acción para eliminar un producto.
     * Este método se llama cuando se realiza una acción en un elemento de la interfaz gráfica relacionado con la eliminación de un producto.
     * Su función principal es llamar al método eliminarProducto().
     * @param event Objeto ActionEvent que representa el evento que desencadenó la acción.
     */
    @FXML
    void eliminarProductoAction(ActionEvent event){
        eliminarProducto();
    }

    /**
     * Acción para agregar un producto.
     * Este método se llama cuando se realiza una acción en un elemento de la interfaz gráfica relacionado con la agregación de un nuevo producto.
     * Su función principal es crear un nuevo producto si los campos son válidos y mostrar una alerta informativa.
     * Además, actualiza la tabla de productos.
     * @param event Objeto ActionEvent que representa el evento que desencadenó la acción.
     */
    @FXML
    void crearProductoAction(ActionEvent event){
        crearProducto();
    }





    /**
     * Inicializa la vista de la interfaz gráfica.
     * Configura el enlace de datos, obtiene productos y realiza la inicialización de campos.
     */
    private void intiView() {
        initDataBinding();
        obtenerProductos();
        inicializarCampos();
        tableProductos.getItems().clear();
        tableProductos.setItems(listaProductosDto);
        listenerSelection();
    }

    /**
     * Configura el enlace de datos para las columnas de la tabla de productos.
     */
    private void initDataBinding() {
        // Configura el enlace de datos para las columnas de la tabla
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigo()));
        tcTipo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().tipoProducto())));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estado()));
    }

    /**
     * Obtiene productos y los agrega a la lista de productos DTO.
     */
    private void obtenerProductos() {
        listaProductosDto.addAll(productoControllerService.obtenerProductos());
    }



    /**
     * Agrega un listener a la selección de la tabla de productos.
     * Este método se encarga de escuchar cuando se selecciona un elemento de la tabla de productos y mostrar su información correspondiente.
     */
    private void listenerSelection() {
        tableProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            productoSeleccionado = newSelection;
            mostrarInformacionProducto(productoSeleccionado);
        });
    }

    /**
     * Muestra la información del producto seleccionado en los campos de texto.
     * @param productoSeleccionado El producto seleccionado en la tabla.
     */
    private void mostrarInformacionProducto(ProductoDto productoSeleccionado) {
        if (productoSeleccionado != null) {
            txtInformation.setText("Nombre: " + productoSeleccionado.nombre() + "\nCodigo: " + productoSeleccionado.codigo()
                    + "\nEstado: " + productoSeleccionado.estado() + "\nTipo: " + productoSeleccionado.tipoProducto());
            txtNombre.setText(productoSeleccionado.nombre());
            txtCodigo.setText(productoSeleccionado.codigo());
            cbxEstado.getSelectionModel().select(productoSeleccionado.estado());
            cbxTipo.getSelectionModel().select(String.valueOf(productoSeleccionado.tipoProducto()));
        }
    }

    /**
     * Crea un nuevo producto.
     * Este método crea un objeto ProductoDto a partir de los datos ingresados en la interfaz gráfica y lo agrega mediante el servicio de controlador.
     * Si se agrega correctamente, se muestra una alerta informativa y se actualiza la tabla de productos.
     */
    private void crearProducto() {
        try {
            if(validarCampos()) {
                ProductoDto productoDto = crearProductoDto();
                if (productoControllerService.agregarProducto(productoDto)) {
                    AlertaUtils.mostrarAlertaInformacion("El producto se agregó correctamente");
                    tableProductos.getItems().clear();
                    obtenerProductos();
                    limpiarCamposProducto();
                }
            }
        }catch (ProductoException e) {
            Persistencia.guardarRegistroLog(e.getMessage(),2,"Excepcion");
            AlertaUtils.mostrarAlertaError(e.getMessage());
            limpiarCamposProducto();

      }
    }

    /**
     * Limpia los campos de entrada de datos en la interfaz gráfica.
     */
    private void limpiarCamposProducto() {
        txtNombre.clear();
        txtCodigo.clear();
        cbxTipo.getSelectionModel().selectFirst();
        cbxEstado.getSelectionModel().selectFirst();

    }

    /**
     * Crea un objeto ProductoDto a partir de los datos ingresados en la interfaz gráfica.
     * @return ProductoDto creado a partir de los datos ingresados.
     */
    private ProductoDto crearProductoDto() {
        return new ProductoDto(txtNombre.getText(),
                cbxTipo.getSelectionModel().getSelectedItem(),
                txtCodigo.getText(),
                cbxEstado.getSelectionModel().getSelectedItem());
    }

    /**
     * Valida que todos los campos de entrada de datos estén completos y sean válidos.
     * @return true si todos los campos están completos y válidos, false en caso contrario.
     */
    private boolean validarCampos() {
        String nombre = txtNombre.getText();
        String codigo = txtCodigo.getText();
        String estado = cbxEstado.getSelectionModel().getSelectedItem();
        String tipoProducto = cbxTipo.getSelectionModel().getSelectedItem();

        // Verifica si alguno de los campos es nulo o vacío
        if (nombre.isEmpty() || codigo.isEmpty() || estado.equals("Selecciona") || tipoProducto.equals("Selecciona")) {
            AlertaUtils.mostrarAlertaInformacion("Por favor complete todos los campos");
            return false; // Retorna false si los campos no están completos
        } else {
            return true; // Retorna true si todos los campos están completos
        }
    }

    /**
     * Actualiza un producto seleccionado.
     * Este método llama al servicio de controlador para actualizar el producto seleccionado con nuevos datos.
     * Si se actualiza correctamente, se muestra una alerta informativa, se actualiza la tabla de productos y se limpian los campos de entrada.
     */
    private void actualizarProducto() {
        try {
            if (productoSeleccionado != null) {
                if (validarCampos()) {
                    ProductoDto productoNuevo = crearProductoDto();
                    if (!productoNuevo.equals(productoSeleccionado)) {
                        if (productoControllerService.actualizarProducto(productoSeleccionado, productoNuevo)) {
                            AlertaUtils.mostrarAlertaInformacion("Se Actualizo correctamente el producto");
                            tableProductos.getItems().clear();
                            obtenerProductos();
                            limpiarCamposProducto();
                        }
                    }else{
                        AlertaUtils.mostrarAlertaError("El producto no sufrio ningun cambio");
                    }
                }
            }else{
                AlertaUtils.mostrarAlertaInformacion("Seleccione un producto");
            }
        }catch (ProductoException e){
            Persistencia.guardarRegistroLog(e.getMessage(),2, "Excepcion");
            AlertaUtils.mostrarAlertaInformacion(e.getMessage());
        }
    }

    /**
     * Elimina un producto seleccionado.
     * Este método llama al servicio de controlador para eliminar el producto seleccionado.
     * Si se elimina correctamente, se muestra una alerta informativa, se actualiza la tabla de productos y se limpian los campos de entrada.
     */
    private void eliminarProducto() {
        try {
            if (productoSeleccionado != null) {
                if (AlertaUtils.mostrarAlertaConfirmacion("¿Está seguro que desea eliminar el producto seleccionado?")) {
                    if (productoControllerService.eliminarProducto(productoSeleccionado)) {
                        AlertaUtils.mostrarAlertaInformacion("Se eliminó correctamente el producto");
                        tableProductos.getItems().clear();
                        obtenerProductos();
                        limpiarCamposProducto();
                    }
                }
            }
        }catch (ProductoException e){
            AlertaUtils.mostrarAlertaError(e.getMessage());
        }
    }

    /**
     * Inicializa los campos de entrada de datos.
     * Este método agrega validadores a los campos de texto para garantizar que los datos ingresados sean válidos.
     */
    private void inicializarCampos() {
        // Validador para el campo de código (solo permite números)
        txtCodigo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCodigo.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Validador para el campo de nombre (solo permite letras)
        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                txtNombre.setText(newValue.replaceAll("[^a-zA-Z]", ""));
            }
        });

        // Crear una lista de los tipos de productos
        List<String> tiposProductos = Arrays.asList("Selecciona", "Tecnologia", "Hogar", "Deporte", "Vehiculo", "Bien raiz");
        ObservableList<String> tiposProductosList = FXCollections.observableArrayList(tiposProductos);
        cbxTipo.setItems(tiposProductosList);

        // Crear una lista de los estados de productos
        List<String> estadosProductos = Arrays.asList("Selecciona", "Nuevo", "Usado");
        ObservableList<String> estadosProductosList = FXCollections.observableArrayList(estadosProductos);
        cbxEstado.setItems(estadosProductosList);
    }

}
