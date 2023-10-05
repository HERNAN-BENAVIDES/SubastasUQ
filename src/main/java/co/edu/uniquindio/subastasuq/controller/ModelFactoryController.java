package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.controller.services.IModelFactoryController;
import co.edu.uniquindio.subastasuq.excepcions.ProductoException;
import co.edu.uniquindio.subastasuq.excepcions.UsuarioException;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.mapping.mappers.UsuarioAnuncianteMapper;
import co.edu.uniquindio.subastasuq.model.Subasta;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;
import co.edu.uniquindio.subastasuq.utils.Persistencia;
import co.edu.uniquindio.subastasuq.utils.SubastaUtils;
import co.edu.uniquindio.subastasuq.utils.UsuarioAnuncianteUtils;

import java.io.IOException;
import java.util.List;

public class ModelFactoryController {

    private static ModelFactoryController instance;
    Subasta subasta;

    public ModelFactoryController() {
        cargarDatosBase();
        salvarDatosPrueba();
        cargarDatosPrueba();
        //cargarDatosArchivoProducto();
    }

    private void cargarDatosPrueba() {
        try {
            Persistencia.cargarCompradores();
            Persistencia.cargarAnunciantes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void cargarDatosArchivoProducto() {
        subasta = new Subasta();

        try {
            Persistencia.cargarDatosArchivo(subasta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void salvarDatosPrueba() {
        try {
            Persistencia.guardarAnunciantes(subasta.getListAnunciantes());
            Persistencia.guardarCompradores(subasta.getListCompradores());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarDatosBase() {
        subasta = SubastaUtils.inicializarDatos();
      //  usuarioAnunciante = UsuarioAnuncianteUtils.inicializarDatos();
    }



    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public boolean iniciarSesion(String user, String password, String tipo) throws UsuarioException, IOException {
        return Persistencia.iniciarSesion(user, password, tipo);
    }

    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }
}
