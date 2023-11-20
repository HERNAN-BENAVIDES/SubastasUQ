package co.edu.uniquindio.subastasuq.config;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitFactory {
    private ConnectionFactory connectionFactory;
    public RabbitFactory() {
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost("localhost");
        this.connectionFactory.setPort(5672);
        this.connectionFactory.setUsername("HERNAN DARIO");
        this.connectionFactory.setPassword("Qg6nUZB5jPJiA$Y");
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
}
