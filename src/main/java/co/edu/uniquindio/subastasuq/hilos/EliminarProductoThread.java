package co.edu.uniquindio.subastasuq.hilos;

import co.edu.uniquindio.subastasuq.controller.ModelFactoryController;
import co.edu.uniquindio.subastasuq.mapping.dto.ProductoDto;
import co.edu.uniquindio.subastasuq.mapping.mappers.ProductoMapper;
import co.edu.uniquindio.subastasuq.model.Producto;
import co.edu.uniquindio.subastasuq.utils.Constantes;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class EliminarProductoThread extends Thread{
    ModelFactoryController modelFactoryController;
    ConnectionFactory factory;
    public EliminarProductoThread(ConnectionFactory connectionFactory) {
        this.modelFactoryController = ModelFactoryController.getInstance();
        this.factory = connectionFactory;
    }

    @Override
    public void run() {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(Constantes.ELIMINAR_PRODUCTO_QUEUE, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                byte[] datosSerializados = delivery.getBody();
                ProductoDto productoDto = ProductoMapper.productoToProductoDto((Producto) deserializarObjeto(datosSerializados));

                // Aquí puedes manejar el nuevo anuncio recibido
                System.out.println("Nuevo producto recibido: " + productoDto);
            };

            channel.basicConsume(Constantes.ELIMINAR_PRODUCTO_QUEUE, true, deliverCallback, consumerTag -> {
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
