package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.excepcions.*;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;
import co.edu.uniquindio.subastasuq.model.UsuarioComprador;
import co.edu.uniquindio.subastasuq.utils.AlertaUtils;


import java.io.IOException;

public class InicioSesionController {

    ModelFactoryController modelFactoryController;

    public InicioSesionController() {
        modelFactoryController = ModelFactoryController.getInstance(); 
    }


    public boolean iniciarSesion(String user, String password, String tipo) throws IOException, AutenticacionException, AutenticacionCompradorException, AutenticacionAnuncianteException {
        if(modelFactoryController.iniciarSesion(user,password,tipo)){
            if(tipo.equals("Anunciante")){
                try {
                    obtenerUsuarioAnunciante(user);
                } catch (UsuarioAnuncianteException e) {
                    AlertaUtils.mostrarAlertaInformacion(e.getMessage());
                }
            }else if (tipo.equals("Comprador")) {
                try {
                    obtenerUsuarioComprador(user);
                } catch (UsuarioCompradorException e) {
                    AlertaUtils.mostrarAlertaInformacion(e.getMessage());
                }
            }
            return true;
        }
        return false;
    }

    private void obtenerUsuarioComprador(String user) throws UsuarioCompradorException {
       UsuarioComprador comprador = modelFactoryController.obtenerComprador(user);
       modelFactoryController.setComprador(comprador);
    }

    private void obtenerUsuarioAnunciante(String user) throws UsuarioAnuncianteException {
        UsuarioAnunciante usuario = modelFactoryController.obtenerAnunciante(user);
        modelFactoryController.setAnunciante(usuario);

    }

    public void guardarCambios() {
        modelFactoryController.guardarCambios();
    }
}
