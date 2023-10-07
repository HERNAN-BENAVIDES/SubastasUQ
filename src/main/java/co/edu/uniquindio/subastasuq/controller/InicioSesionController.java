package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.excepcions.AutenticacionException;
import co.edu.uniquindio.subastasuq.excepcions.UsuarioException;
import co.edu.uniquindio.subastasuq.model.UsuarioAnunciante;
import co.edu.uniquindio.subastasuq.utils.AlertaUtils;


import java.io.IOException;

public class InicioSesionController {

    ModelFactoryController modelFactoryController;

    public InicioSesionController() {
        modelFactoryController = ModelFactoryController.getInstance(); 
    }


    public boolean iniciarSesion(String user, String password, String tipo) throws IOException, AutenticacionException {
        if(modelFactoryController.iniciarSesion(user,password,tipo)){
            if(tipo.equals("Anunciante")){
                try {
                    obtenerUsuarioAnunciante(user);
                } catch (UsuarioException e) {
                    AlertaUtils.mostrarAlertaInformacion(e.getMessage());
                }
            }else if (tipo.equals("Comprador")) {
                obtenerUsuarioComprador(user);
            }
            return true;
        }
        return false;
    }

    private void obtenerUsuarioComprador(String user) {
//        UsuarioComprador comprador = modelFactoryController.obtenerUsuarioComprador(user,password);
//       ProductoViewController ventana = new ProductoViewController();
//       ventana.setUsuario(comprador);
    }

    private void obtenerUsuarioAnunciante(String user) throws UsuarioException {
        UsuarioAnunciante usuario = modelFactoryController.obtenerUsuarioAnunciante(user);
        modelFactoryController.setUsuarioAnunciante(usuario);

    }
}
