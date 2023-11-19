package co.edu.uniquindio.subastasuq.viewController;

import co.edu.uniquindio.subastasuq.controller.CompradorController;
import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CompradorViewController {
    @FXML
    private CheckBox bienRaizCheckBox;
    @FXML
    private CheckBox deportesCheckBox;
    @FXML
    private CheckBox hogarCheckBox;
    @FXML
    private CheckBox tecnologiaCheckBox;
    @FXML
    private CheckBox vehiculosCheckBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextArea txtInformation;
    private CompradorController compradorControllerService;
    private final List<AnuncioDto> listAnuncios = new ArrayList<>();
    private final List<AnuncioDto> listAnunciosFiltrada = new ArrayList<>();
    private AnuncioDto anuncioSeleccionado;


    @FXML
    void initialize() {
        compradorControllerService = new CompradorController();
        intiView();
    }

    private void intiView() {
        obtenerAnuncios();
        mostrarAnuncios();
//        listenerCheckBox();
    }

    private void obtenerAnuncios() {
        listAnuncios.clear();
        listAnuncios.addAll(compradorControllerService.obtenerListaAnuncios());
    }

    private void mostrarAnuncios() {
        scrollPane.setContent(null);
        GridPane gridPane = crearGridPane(listAnuncios);
        scrollPane.setContent(gridPane);
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
        });

        Label nombre = new Label("Nombre: " + anuncioDto.nombreAnuncio());
        Label precio = new Label("Precio: " + anuncioDto.pujaMasAlta());

        VBox vbox = new VBox(imagenView, nombre, precio);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        return vbox;
    }
    private void mostrarDetallesAnuncio(AnuncioDto anuncioDto) {
        txtInformation.setText("Nombre: " + anuncioDto.nombreAnuncio() + "\nCodigo: " + anuncioDto.codigoAnuncio()
                + "\nDescripcion: " + anuncioDto.descripcionAnuncio() + "\nPuja mas alta: " + anuncioDto.pujaMasAlta() +
                "\n Fecha cierre: " + anuncioDto.fechaFinal() + anuncioDto.horaFinal());
    }
    private String formatofecha(LocalDateTime localDateTime) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return localDateTime.format(formato);
    }

    @FXML
    void actualizarListaAnuncios(ActionEvent event) {
        actualizarAnuncios();
    }

    private void actualizarAnuncios() {
        listAnunciosFiltrada.clear();
        if(!tecnologiaCheckBox.isSelected() && !hogarCheckBox.isSelected() && !deportesCheckBox.isSelected() &&
            !vehiculosCheckBox.isSelected() && !bienRaizCheckBox.isSelected()){
            listAnunciosFiltrada.addAll(listAnuncios);
        }

        for (AnuncioDto anuncio : listAnuncios) {
            if ((tecnologiaCheckBox.isSelected() && anuncio.tipoAnuncio().equals("Tecnologia")) ||
                    (hogarCheckBox.isSelected() && anuncio.tipoAnuncio().equals("Hogar")) ||
                    (deportesCheckBox.isSelected() && anuncio.tipoAnuncio().equals("Deporte")) ||
                    (vehiculosCheckBox.isSelected() && anuncio.tipoAnuncio().equals("Vehiculo")) ||
                    (bienRaizCheckBox.isSelected() && anuncio.tipoAnuncio().equals("Bien raiz"))) {
                listAnunciosFiltrada.add(anuncio);
            }
        }
        actualizarScroll(listAnunciosFiltrada);

    }

    private void actualizarScroll(List<AnuncioDto> listAnunciosFiltrada) {
        scrollPane.setContent(null);
        GridPane gridPane = crearGridPane(listAnunciosFiltrada);
        scrollPane.setContent(gridPane);
    }
}
