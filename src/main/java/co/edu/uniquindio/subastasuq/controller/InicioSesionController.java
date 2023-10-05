package co.edu.uniquindio.subastasuq.controller;

import co.edu.uniquindio.subastasuq.excepcions.UsuarioException;

import java.io.IOException;

public class InicioSesionController {

    ModelFactoryController modelFactoryController;

    public InicioSesionController() {
        modelFactoryController = new ModelFactoryController();
    }


    public boolean iniciarSesion(String user, String password, String tipo) throws UsuarioException, IOException {
        return modelFactoryController.iniciarSesion(user,password,tipo);
    }
}
