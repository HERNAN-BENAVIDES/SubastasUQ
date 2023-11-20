package co.edu.uniquindio.subastasuq.viewController;

import co.edu.uniquindio.subastasuq.controller.AnuncioController;
import co.edu.uniquindio.subastasuq.excepcions.AnuncioException;
import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.mapping.dto.PujaDto;
import co.edu.uniquindio.subastasuq.mapping.dto.UsuarioAnuncianteDto;
import co.edu.uniquindio.subastasuq.utils.AlertaUtils;
import co.edu.uniquindio.subastasuq.utils.Persistencia;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AnuncioViewController {

    @FXML
    private ComboBox<String> cbxElegirProducto;

    @FXML
    private Label lblFoto;

    @FXML
    private TextField txtCodigoAnuncio;

    @FXML
    private TextArea txtDescripcionAnuncio;

    @FXML
    private TextArea txtInformation;

    @FXML
    private TableView<PujaDto> tablePujas;

    @FXML
    private TableColumn<PujaDto, String> tcFecha;

    @FXML
    private TableColumn<PujaDto, String> tcPuja;

    @FXML
    private TableColumn<PujaDto, String> tcUsuario;
    @FXML
    private TableColumn<PujaDto, String> tcIsAceptada;

    @FXML
    private RadioButton rbAnunciosActivos;

    @FXML
    private RadioButton rbAnunciosInactivos;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TextField txtHora;

    @FXML
    private TextField txtNombreAnuncio;

    @FXML
    private TextField txtPrecioInicial;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ToggleGroup anunciosGrupe;

    @FXML
    private Button btAceptarOferta;

    @FXML
    private Button btActualizarAnuncio;

    @FXML
    private Button btCrearAnuncio;

    @FXML
    private Button btLimpiarCampos;

    private AnuncioController anuncioControllerService;
    private final List<AnuncioDto> listAnunciosActivos = new ArrayList<>();
    private final List<AnuncioDto> listAnunciosInactivos = new ArrayList<>();
    private AnuncioDto anuncioSeleccionado;
    private final ObservableList<PujaDto> listPujas = FXCollections.observableArrayList();
    private PujaDto pujaSeleccionada;
    private String rutaArchivoCsv;


    @FXML
    void initialize() {
        anuncioControllerService = new AnuncioController();
        intiView();
    }

    private void intiView() {
        obtenerAnuncios();
        inicializarCampos();
        mostrarAnunciosActivos();
        inicializarPujas();
        listenerSelection();
    }

    private void obtenerAnuncios() {
        listAnunciosActivos.clear();
        listAnunciosInactivos.clear();
        listAnunciosActivos.addAll(anuncioControllerService.obtenerListaAnunciosActivos());
        listAnunciosInactivos.addAll(anuncioControllerService.obtenerListaAnunciosInactivos());
    }

    private void inicializarCampos() {
        // Validador para el campo de código (solo permite números)
        txtCodigoAnuncio.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCodigoAnuncio.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Validador para el campo de precio inicial (permite números y decimales)
        txtPrecioInicial.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                txtPrecioInicial.setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });

        // Configurar un listener para el grupo de RadioButtons
        anunciosGrupe.selectedToggleProperty().addListener((ov, oldToggle, newToggle) -> {
            if (newToggle != null) {
                if (rbAnunciosActivos.isSelected()) {
                    btAceptarOferta.setVisible(true); // Mostrar el botón
                    btActualizarAnuncio.setVisible(true);
                    btCrearAnuncio.setVisible(true);
                    btLimpiarCampos.setVisible(true);
                } else if (rbAnunciosInactivos.isSelected()) {
                    btAceptarOferta.setVisible(false); // Ocultar el botón
                    btActualizarAnuncio.setVisible(false);
                    btCrearAnuncio.setVisible(false);
                    btLimpiarCampos.setVisible(false);
                }
            }
        });

        dpFecha.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Cambia el estilo de las fechas pasadas (opcional)
                }
            }
        });
        inicializarCombobox();
    }

    void inicializarCombobox() {
        // Crear una lista de los tipos de productos
        List<String> productos = Arrays.asList(anuncioControllerService.obtenerProductos());
        ObservableList<String> productosList = FXCollections.observableArrayList(productos);
        cbxElegirProducto.setItems(productosList);
        cbxElegirProducto.getSelectionModel().selectFirst();
        cbxElegirProducto.setOnShowing(event -> {
            actualizarComboBox();
        });
    }

    void actualizarComboBox() {
        // Crear una lista de los tipos de productos
        List<String> productos = Arrays.asList(anuncioControllerService.obtenerProductos());
        ObservableList<String> productosList = FXCollections.observableArrayList(productos);
        cbxElegirProducto.setItems(productosList);
    }

    private void inicializarPujas() {
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(formatofecha(cellData.getValue().fecha())));
        tcPuja.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().oferta())));
        tcUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().compradorAsociado().username())));
    }

    private void listenerSelection() {
        tablePujas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            pujaSeleccionada = newSelection;
        });
    }

    private void obtenerPujas() {
        listPujas.addAll(anuncioControllerService.obtenerListaPujasAnuncio(anuncioSeleccionado));
    }
    
    private GridPane crearGridPane(List<AnuncioDto> listAnuncios) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int columnas = 0;
        int filas = 0;

        for (AnuncioDto anuncioDto : listAnuncios) {
            VBox vbox = getVbox(anuncioDto);

            // Establecer un margen superior para ajustar desde el píxel 30 hacia abajo
            VBox.setMargin(vbox, new Insets(50, 0, 0, 0));

            gridPane.add(vbox, columnas, filas);

            columnas++;
            if (columnas == 3) {
                columnas = 0;
                filas++;
            }
        }
        return gridPane;
    }

    private VBox getVbox(AnuncioDto anuncioDto) {
        Image imagen = new Image(anuncioDto.fotoAnuncio());
        ImageView imagenView = new ImageView(imagen);
        imagenView.setFitWidth(188);
        imagenView.setFitHeight(188);

        // Agregar evento de clic a la imagen
        imagenView.setOnMouseClicked(event -> {
            anuncioSeleccionado = anuncioDto;
            mostrarDetallesAnuncio(anuncioDto);
            actualizarTabla();
        });

        Label nombre = new Label("Nombre: " + anuncioDto.nombreAnuncio());
        Label precio = new Label("Precio: " + anuncioDto.pujaMasAlta());

        VBox vbox = new VBox(imagenView, nombre, precio);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        return vbox;
    }

    private void actualizarTabla() {
        tablePujas.getItems().clear();
        obtenerPujas();
        tablePujas.setItems(listPujas);
    }


    private void mostrarDetallesAnuncio(AnuncioDto anuncioDto) {
        txtInformation.setText("Nombre: " + anuncioDto.nombreAnuncio() + "\nCodigo: " + anuncioDto.codigoAnuncio()
                + "\nDescripcion: " + anuncioDto.descripcionAnuncio() + "\nPuja mas alta: " + anuncioDto.pujaMasAlta() +
                "\n Fecha cierre: " + anuncioDto.horaFinal() + " " + formatoFecha(anuncioDto.fechaFinal()));
        txtNombreAnuncio.setText(anuncioDto.nombreAnuncio());
        txtCodigoAnuncio.setText(anuncioDto.codigoAnuncio());
        dpFecha.setValue(anuncioDto.fechaFinal());
        txtHora.setText(String.valueOf(anuncioDto.horaFinal()));
        txtDescripcionAnuncio.setText(anuncioDto.descripcionAnuncio());
        lblFoto.setText(anuncioDto.fotoAnuncio());
        txtPrecioInicial.setText(String.valueOf(anuncioDto.precioInicial()));
        cbxElegirProducto.getSelectionModel().select(anuncioDto.productoAsociado().nombre());
    }

    private String formatoFecha(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy");
        return localDate.format(formatter);
    }

    public String formatofecha(LocalDateTime fecha) {
        if (fecha != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm dd-MM-yyyy");
            return fecha.format(formatter);
        } else {
            return "Fecha no disponible";
        }
    }


    @FXML
    void actualizarAnuncioAction(ActionEvent event) {
        actualizarAnuncio();
    }

    private void actualizarAnuncio() {
        try {
            if (anuncioSeleccionado != null) {
                if (validarCampos()) {
                    AnuncioDto anuncioDto = crearAnuncioDto();
                    if (!anuncioDto.equals(anuncioSeleccionado)) {
                        if (anuncioControllerService.actualizarAnuncio(anuncioSeleccionado, anuncioDto)) {
                            AlertaUtils.mostrarAlertaInformacion("Se actualizo correctamente el anuncio");
                            actualizarAnuncios();
                            limpiarCamposAnuncio();
                        }
                    } else {
                        AlertaUtils.mostrarAlertaError("El anuncio no sufrio ningun cambio");
                    }
                }
            } else {
                AlertaUtils.mostrarAlertaInformacion("Seleccione un anuncio");
            }
        } catch (AnuncioException e) {
            Persistencia.guardarRegistroLog(e.getMessage(), 2, "Excepcion");
            AlertaUtils.mostrarAlertaInformacion(e.getMessage());
        }
    }

    @FXML
    void crearAnuncioAccion(ActionEvent event) {
        crearAnuncio();
    }

    private void crearAnuncio() {
        try {
            if (validarCampos()) {
                AnuncioDto anuncioDto = crearAnuncioDto();
                if (anuncioControllerService.agregarAnuncio(anuncioDto)) {
                    AlertaUtils.mostrarAlertaInformacion("El anuncio se agregó correctamente");
                    actualizarAnuncios();
                    limpiarCamposAnuncio();
                }
            }
        } catch (AnuncioException e) {
            Persistencia.guardarRegistroLog(e.getMessage(), 2, "Excepcion");
            AlertaUtils.mostrarAlertaError(e.getMessage());
        }
    }

    private void actualizarAnuncios() {
        obtenerAnuncios();
        scrollPane.setContent(null);

        if(rbAnunciosActivos.isSelected()){
            mostrarAnunciosActivos();
        }

        else if(rbAnunciosInactivos.isSelected()){
            mostrarAnunciosInactivos();
        }

        txtInformation.clear();

    }



    private void limpiarCamposAnuncio() {
        txtNombreAnuncio.clear();
        txtCodigoAnuncio.clear();
        txtDescripcionAnuncio.clear();
        lblFoto.setText("");
        cbxElegirProducto.getSelectionModel().selectFirst();
        txtPrecioInicial.clear();
        txtHora.clear();
        dpFecha.setValue(null);
        tablePujas.getItems().clear();
        txtInformation.clear();
    }

    private AnuncioDto crearAnuncioDto() {
        return new AnuncioDto(txtNombreAnuncio.getText(), txtCodigoAnuncio.getText(), dpFecha.getValue(), obtenerHora(), txtDescripcionAnuncio.getText(),
                lblFoto.getText(), obtenerProducto(), obtenerAnunciante(), Double.parseDouble(txtPrecioInicial.getText()), Double.parseDouble(txtPrecioInicial.getText()),true, obtenerProducto().tipoProducto(), new ArrayList<>());
    }

    private UsuarioAnuncianteDto obtenerAnunciante() {
        return anuncioControllerService.getAnunciante();
    }

    private LocalTime obtenerHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(txtHora.getText(), formatter);
    }

    private boolean validarCampos() {
        String nombre = txtNombreAnuncio.getText().trim();
        String codigo = txtCodigoAnuncio.getText().trim();
        String fecha = dpFecha.getValue().toString();
        String hora = txtHora.getText();
        String descripcion = txtDescripcionAnuncio.getText().trim();
        ProductoDto producto = obtenerProducto();
        String foto = lblFoto.getText().trim();

        // Verificar que ninguno de los campos esté vacío
        if (nombre.isEmpty() || codigo.isEmpty() || descripcion.isEmpty() || producto == null || foto.isEmpty()
                || fecha.isEmpty() || hora.isEmpty()) {
            AlertaUtils.mostrarAlertaInformacion("Por favor complete todos los campos");
            return false;
        }


        // Verificar que el campo de precio no esté vacío y sea un número válido
        try {
            Double precioInicial = Double.valueOf(txtPrecioInicial.getText().trim());
        } catch (NumberFormatException e) {
            AlertaUtils.mostrarAlertaError("Ingrese un numero valido");
            return false;
        }

        // Todos los campos están completos y el precio inicial es válido
        return true;
    }


    private ProductoDto obtenerProducto() {
        String nombreProducto = cbxElegirProducto.getSelectionModel().getSelectedItem();
        return anuncioControllerService.obtenerProductoSeleccionado(nombreProducto);
    }

    @FXML
    void eliminarAnuncioAction(ActionEvent event) {
        eliminarAnuncio();
    }

    private void eliminarAnuncio() {
        try {
            if (anuncioSeleccionado != null) {
                if (AlertaUtils.mostrarAlertaConfirmacion("¿Está seguro que desea eliminar el anuncio seleccionado?")) {
                    if (anuncioControllerService.eliminarAnuncio(anuncioSeleccionado)) {
                        AlertaUtils.mostrarAlertaInformacion("Se eliminó correctamente el anuncio");
                        actualizarAnuncios();
                        limpiarCamposAnuncio();
                        anuncioSeleccionado = null;
                    }
                }
            }else{
                AlertaUtils.mostrarAlertaError("Debe seleccionar un anuncio y una oferta");
            }
        } catch (AnuncioException e) {
            Persistencia.guardarRegistroLog(e.getMessage(), 2, "Excepcion");
            AlertaUtils.mostrarAlertaError(e.getMessage());
        }
    }

    @FXML
    void limpiarCamposAction(ActionEvent event) {
        limpiarCamposAnuncio();
    }

    @FXML
    void seleccionarFotoAccion(ActionEvent event) {
        File selectedFile = seleccionarFoto();
        if (selectedFile != null) {
            String rutaImagen = selectedFile.getAbsolutePath();
            lblFoto.setText(rutaImagen);
        }
    }

    private File seleccionarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        return fileChooser.showOpenDialog(null);
    }

    @FXML
    void mostrarAnunciosActivosAction(ActionEvent event) {
        mostrarAnunciosActivos();
    }

    private void mostrarAnunciosActivos() {
        scrollPane.setContent(null);
        GridPane gridPane = crearGridPane(listAnunciosActivos);
        scrollPane.setContent(gridPane);
        actualizarTablaPujas(false);
        limpiarCamposAnuncio();
    }

    @FXML
    void mostrarAnunciosInactivosAction(ActionEvent event) {
        mostrarAnunciosInactivos();
    }

    private void mostrarAnunciosInactivos() {
        scrollPane.setContent(null);
        GridPane gridPane = crearGridPane(listAnunciosInactivos);
        scrollPane.setContent(gridPane);
        actualizarTablaPujas(true);
        limpiarCamposAnuncio();
    }

    private void actualizarTablaPujas(boolean anunciosInactivos) {
        if (anunciosInactivos) {
            // Mostrar la columna solo si no está presente
            if (tcIsAceptada == null) {
                tcIsAceptada = new TableColumn<>("Aceptada");
                tablePujas.getColumns().add(tcIsAceptada);
                tcIsAceptada.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isAceptada() ? "Aceptada" : "No aceptada"));
            }
        } else {
            // Eliminar la columna si está presente
            if (tcIsAceptada != null) {
                tablePujas.getColumns().remove(tcIsAceptada);
                tcIsAceptada = null;
            }
        }
        tablePujas.refresh();
    }




    @FXML
    void acertarOfertaAction(ActionEvent event) {
        aceptarOfertaAnuncio();
    }

    private void aceptarOfertaAnuncio() {
        if (AlertaUtils.mostrarAlertaConfirmacion("Esta apunto de aceptar la oferta: " + pujaSeleccionada.oferta())){
            try {
                anuncioControllerService.cerrarAnuncio(anuncioSeleccionado, pujaSeleccionada);
                AlertaUtils.mostrarAlertaInformacion("Se acepto la oferta");

                actualizarAnuncios();
                limpiarCamposAnuncio();
            } catch (AnuncioException e) {
                Persistencia.guardarRegistroLog(e.getMessage(), 2, "Excepcion");
                throw new RuntimeException(e);
            }
        }


    }

    @FXML
    void exportarAnunciosAction(ActionEvent event) {
        seleccionarRuta();
        exportarAnunciosCsv();
    }

    private void exportarAnunciosCsv() {
        obtenerAnuncios();
        if(anuncioControllerService.exportarCvs(listAnunciosActivos, rutaArchivoCsv)){
            AlertaUtils.mostrarAlertaInformacion("Se guardo el archivo");
        }
    }

    private void seleccionarRuta() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Archivo CSV");

        // Configurar el filtro para mostrar solo archivos CSV
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostrar el cuadro de diálogo de guardado
        File selectedFile = fileChooser.showSaveDialog(new Stage());

        if (selectedFile != null) {
            rutaArchivoCsv = selectedFile.getAbsolutePath();
        }
    }
}