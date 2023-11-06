package co.edu.uniquindio.subastasuq.viewController;

import co.edu.uniquindio.subastasuq.controller.AnuncioController;
import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private TextField txtNombreAnuncio;

    @FXML
    private TextField txtPrecioInicial;

    @FXML
    private ScrollPane scrollPane;

    private AnuncioController anuncioControllerService;
    private List<AnuncioDto> listAnuncios = new ArrayList<>();


    @FXML
    void initialize() {
        anuncioControllerService = new AnuncioController();
        listAnuncios = anuncioControllerService.obtenerListaAnuncios();
        intiView();
    }

    private void intiView() {
        GridPane gridPane = crearGridPane();
        scrollPane.setContent(gridPane);
        inicializarCampos();
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


        // Crear una lista de los tipos de productos
        List<String> productos = Arrays.asList(anuncioControllerService.obtenerProductos());
        ObservableList<String> productosList = FXCollections.observableArrayList(productos);
        cbxElegirProducto.setItems(productosList);
        cbxElegirProducto.getSelectionModel().selectFirst();




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
            imagenView.setFitWidth(200);
            imagenView.setFitHeight(200);

            // Agregar evento de clic a la imagen
            imagenView.setOnMouseClicked(event -> mostrarDetallesAnuncio(anuncioDto));

            Label nombre = new Label("Nombre: " + anuncioDto.nombreAnuncio());
            Label precio = new Label("Precio: " + anuncioDto.pujaMasAlta());

            VBox vbox = new VBox(imagenView, nombre, precio);
            vbox.setSpacing(10);

            // Establecer un margen superior para ajustar desde el píxel 30 hacia abajo
            VBox.setMargin(vbox, new Insets(30, 0, 0, 0));

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
        + "\nDescripcion: " + anuncioDto.descripcionAnuncio() + "\nPuja mas alta: " + anuncioDto.pujaMasAlta());
    }

    @FXML
    void actualizarAnuncioAction(ActionEvent event) {

    }

    @FXML
    void cerrarAnuncioAction(ActionEvent event) {

    }

    @FXML
    void crearAnuncioAccion(ActionEvent event) {

    }

    @FXML
    void eliminarAnuncioAction(ActionEvent event) {

    }

    @FXML
    void limpiarCamposAction(ActionEvent event) {

    }

    @FXML
    void seleccionarFotoAccion(ActionEvent event) {
        File selectedFile = seleccionarFoto();
        if (selectedFile != null) {
            String rutaImagenNueva = copiarImagen(selectedFile);
            String rutaImagen = selectedFile.getAbsolutePath();
            lblFoto. setText(rutaImagen);
        }
    }

    private File seleccionarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        return fileChooser.showOpenDialog(null);
    }

    private String copiarImagen(File selectedFile) {
        if (selectedFile != null) {
            try {
                Path sourcePath = selectedFile.toPath();
                Path destination = Path.of("src/main/resources/fotos/" + selectedFile.getName());
                Files.copy(sourcePath, destination, StandardCopyOption.REPLACE_EXISTING);
                return destination.toString(); // Devuelve la ruta de la imagen copiada
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; // En caso de error o si no se seleccionó un archivo
    }

}
