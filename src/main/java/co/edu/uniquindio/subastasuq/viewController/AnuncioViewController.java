package co.edu.uniquindio.subastasuq.viewController;


import co.edu.uniquindio.subastasuq.controller.AnuncioController;
import co.edu.uniquindio.subastasuq.excepcions.AnuncioException;
import co.edu.uniquindio.subastasuq.excepcions.ProductoException;
import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.mapping.dto.PujaDto;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AnuncioViewController {

    @FXML
    private GridPane gripane;

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
    private DatePicker dpFecha;

    @FXML
    private TextField txtHora;

    @FXML
    private TextField txtNombreAnuncio;

    @FXML
    private TextField txtPrecioInicial;

    @FXML
    private ScrollPane scrollPane;

    private AnuncioController anuncioControllerService;
    private List<AnuncioDto> listAnuncios = new ArrayList<>();
    private AnuncioDto elementoSeleccionado;



    @FXML
    void initialize() {
        anuncioControllerService = new AnuncioController();
        intiView();
    }

    private void intiView() {
        obtenerAnuncios();
        GridPane gridPane = crearGridPane();
        scrollPane.setContent(gridPane);
        inicializarCampos();
        inicializarPujas();
    }

    private void inicializarPujas() {
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty());
        tcPuja.setCellValueFactory(cellData -> new SimpleStringProperty());
        tcUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().anuncioAsociado())));

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

        //timePicker = new JFXTimePicker();
        //timePicker.set24HourView(true);
        //gripane.add(timePicker,2,4);

        inicializarCombobox();
    }


    void inicializarCombobox() {
        // Crear una lista de los tipos de productos
        List<String> productos = Arrays.asList(anuncioControllerService.obtenerProductos());
        ObservableList<String> productosList = FXCollections.observableArrayList(productos);
        cbxElegirProducto.setItems(productosList);
        cbxElegirProducto.getSelectionModel().selectFirst();
        cbxElegirProducto.setOnShowing(event -> {actualizarComboBox();});
    }

    void actualizarComboBox() {
        // Crear una lista de los tipos de productos
        List<String> productos = Arrays.asList(anuncioControllerService.obtenerProductos());
        ObservableList<String> productosList = FXCollections.observableArrayList(productos);
        cbxElegirProducto.setItems(productosList);
    }

    private GridPane crearGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int columnas = 0;
        int filas = 0;

        for (AnuncioDto anuncioDto : listAnuncios) {
            Image imagen = new Image(anuncioDto.fotoAnuncio());
            ImageView imagenView = new ImageView(imagen);
            imagenView.setFitWidth(188);
            imagenView.setFitHeight(188);

            // Agregar evento de clic a la imagen
            imagenView.setOnMouseClicked(event -> {
                elementoSeleccionado = anuncioDto;
                mostrarDetallesAnuncio(anuncioDto);
            });

            Label nombre = new Label("Nombre: " + anuncioDto.nombreAnuncio());
            Label precio = new Label("Precio: " + anuncioDto.pujaMasAlta());

            VBox vbox = new VBox(imagenView, nombre, precio);
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);

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


    private void mostrarDetallesAnuncio(AnuncioDto anuncioDto) {
        txtInformation.setText("Nombre: " + anuncioDto.nombreAnuncio() + "\nCodigo: " + anuncioDto.codigoAnuncio()
        + "\nDescripcion: " + anuncioDto.descripcionAnuncio() + "\nPuja mas alta: " + anuncioDto.pujaMasAlta() +
        "\n Fecha cierre: " + formatofecha(anuncioDto.fechaFinal()));
        txtNombreAnuncio.setText(anuncioDto.nombreAnuncio());
        txtCodigoAnuncio.setText(anuncioDto.codigoAnuncio());
        dpFecha.setValue(LocalDate.from(anuncioDto.fechaFinal()));
        txtHora.setText(obtenerHora(anuncioDto.fechaFinal()));
        txtDescripcionAnuncio.setText(anuncioDto.descripcionAnuncio());
        lblFoto.setText(anuncioDto.fotoAnuncio());
        txtPrecioInicial.setText(String.valueOf(anuncioDto.precioInicial()));
        cbxElegirProducto.getSelectionModel().select(anuncioDto.productoAsociado().nombre());



    }

    private String obtenerHora(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return localDateTime.format(formatter);
    }

    private String formatofecha(LocalDateTime localDateTime) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return localDateTime.format(formato);
    }

    @FXML
    void actualizarAnuncioAction(ActionEvent event) {
        actualizarAnuncio();
    }

    private void actualizarAnuncio() {
        try {
            if (elementoSeleccionado != null) {
                if (validarCampos()) {
                    AnuncioDto anuncioDto = crearAnuncioDto();
                    if (!anuncioDto.equals(elementoSeleccionado)) {
                        if (anuncioControllerService.actualizarAnuncio(elementoSeleccionado, anuncioDto)) {
                            AlertaUtils.mostrarAlertaInformacion("Se actualizo correctamente el anuncio");
                            actualizarAnuncios();
                            limpiarCamposAnuncio();
                        }
                    }else{
                        AlertaUtils.mostrarAlertaError("El anuncio no sufrio ningun cambio");
                    }
                }
            }else{
                AlertaUtils.mostrarAlertaInformacion("Seleccione un anuncio");
            }
        }catch (AnuncioException e){
            Persistencia.guardarRegistroLog(e.getMessage(),2, "Excepcion");
            AlertaUtils.mostrarAlertaInformacion(e.getMessage());
        }
    }

    @FXML
    void cerrarAnuncioAction(ActionEvent event) {

    }

    @FXML
    void crearAnuncioAccion(ActionEvent event) {
        crearAnuncio();
    }

    private void crearAnuncio() {
        try {
            if(validarCampos()){
                AnuncioDto anuncioDto = crearAnuncioDto();
                    if (anuncioControllerService.agregarAnuncio(anuncioDto)) {
                        AlertaUtils.mostrarAlertaInformacion("El anuncio se agregó correctamente");
                        actualizarAnuncios();
                        limpiarCamposAnuncio();
                    }
            }
        }catch (AnuncioException e) {
            AlertaUtils.mostrarAlertaError(e.getMessage());
        }
    }

    private void actualizarAnuncios() {
        listAnuncios = anuncioControllerService.obtenerListaAnuncios();
        txtInformation.clear();
        scrollPane.setContent(null);
        GridPane gridPane = crearGridPane();
        scrollPane.setContent(gridPane);
    }

    private void obtenerAnuncios() {
        listAnuncios.addAll(anuncioControllerService.obtenerListaAnuncios());
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
    }

    private AnuncioDto crearAnuncioDto() {
        return new AnuncioDto(txtNombreAnuncio.getText(), txtCodigoAnuncio.getText(), obtenerFecha(), txtDescripcionAnuncio.getText(),
                lblFoto.getText(), obtenerProducto(), Double.parseDouble(txtPrecioInicial.getText()), Double.parseDouble(txtPrecioInicial.getText()));
    }

    private LocalDateTime obtenerFecha() {
        return LocalDateTime.of(dpFecha.getValue(), Objects.requireNonNull(parseHora()));
    }

    private LocalTime parseHora() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(txtHora.getText(), formatter);
        } catch (Exception e) {
            return null;
        }
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
        return  anuncioControllerService.obtenerProductoSeleccionado(nombreProducto);
    }

    @FXML
    void eliminarAnuncioAction(ActionEvent event) {
        eliminarAnuncio();
    }

    private void eliminarAnuncio() {
        try {
            if (elementoSeleccionado != null) {
                if (AlertaUtils.mostrarAlertaConfirmacion("¿Está seguro que desea eliminar el anuncio seleccionado?")) {
                    if (anuncioControllerService.eliminarAnuncio(elementoSeleccionado)) {
                        AlertaUtils.mostrarAlertaInformacion("Se eliminó correctamente el anuncio");
                        actualizarAnuncios();
                        limpiarCamposAnuncio();
                        elementoSeleccionado = null;
                    }
                }
            }
        }catch (AnuncioException e){
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

    /**
     * Metodo para revisar
     * @param ruta
     * @return
     */
    private String copiarImagen(String ruta) {
        File selectedFile = new File(ruta);
        if (selectedFile.exists()) {
            try {
                Path sourcePath = selectedFile.toPath();
                Path destination = Path.of("src/main/resources/fotos/" + selectedFile.getName());
                Files.copy(sourcePath, destination, StandardCopyOption.REPLACE_EXISTING);
                System.out.println(destination);
                return destination.toString(); // Devuelve la ruta de la imagen copiada
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; // En caso de error o si no se seleccionó un archivo
    }


}
