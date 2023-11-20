package co.edu.uniquindio.subastasuq.hilos;

import co.edu.uniquindio.subastasuq.controller.ModelFactoryController;
import co.edu.uniquindio.subastasuq.excepcions.AnuncioException;
import co.edu.uniquindio.subastasuq.mapping.dto.AnuncioDto;
import co.edu.uniquindio.subastasuq.mapping.mappers.AnuncioMapper;
import co.edu.uniquindio.subastasuq.model.Anuncio;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import static co.edu.uniquindio.subastasuq.utils.Constantes.CREAR_ANUNCIO_QUEUE;

public class CrearAnuncioThread extends Thread {
    ModelFactoryController modelFactoryController;
    private ConnectionFactory factory;

    public CrearAnuncioThread(ConnectionFactory connectionFactory) {
        modelFactoryController = ModelFactoryController.getInstance();
        factory = connectionFactory;
    }

    @Override
    public void run() {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(CREAR_ANUNCIO_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                byte[] datosSerializados = delivery.getBody();
                AnuncioDto anuncioDto = AnuncioMapper.anuncioToAnuncioDto((Anuncio) deserializarObjeto(datosSerializados));

                try {
                    modelFactoryController.agregarAnuncio(anuncioDto);
                } catch (AnuncioException e) {
                    throw new RuntimeException(e);
                }
            };

            channel.basicConsume(CREAR_ANUNCIO_QUEUE, true, deliverCallback, consumerTag -> {
            });

            // Mantener el hilo en ejecución para seguir escuchando mensajes
            while (true) {
                // Puedes agregar un pequeño retraso aquí para evitar un uso excesivo de CPU
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object deserializarObjeto(byte[] datosSerializados) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(datosSerializados);
             ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}