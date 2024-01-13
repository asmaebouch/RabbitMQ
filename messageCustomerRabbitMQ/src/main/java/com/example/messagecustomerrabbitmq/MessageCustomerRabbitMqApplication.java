package com.example.messagecustomerrabbitmq;

import com.rabbitmq.client.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class MessageCustomerRabbitMqApplication {
private static String QUEUE_NAME="hello";
    public static void main(String[] args) {
try{
    ConnectionFactory factory=new ConnectionFactory();
    factory.setUri("amqp://guest:guest@localhost:5672");

    //Create connection and channel
    try(Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()
    ){
        channel.queueDeclare(QUEUE_NAME,false
        ,false,false,null
        );
        DefaultConsumer consumer=new DefaultConsumer(
                channel
        ){
            @Override
            public void handleDelivery (String
                                         consumerTag, Envelope enelope,
                                        AMQP.BasicProperties properties
            , byte[] body) throws IOException{
                String message = new String(body,"UTF-8");
                System.out.println("Received message::"+message);
            }
        };
        //Start consuing messages
        channel.basicConsume(QUEUE_NAME,true,consumer);
        System.out.println(
                "Waiting for mesages . To exist press CTRL+C"
        );
        Thread.sleep(10000);
    } catch (IOException e) {
        throw new RuntimeException(e);
    } catch (InterruptedException | TimeoutException e) {
        throw new RuntimeException(e);
    }


} catch (URISyntaxException e) {
    throw new RuntimeException(e);
} catch (NoSuchAlgorithmException e) {
    throw new RuntimeException(e);
} catch (KeyManagementException e) {
    throw new RuntimeException(e);
}

    }

}
