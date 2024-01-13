package com.example.messageproducerrabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class MessageProducerRabbitMqApplication {
    private final static String QUEUE_NAME="hello";


    public static void main(String[] args) {

        try{
            ConnectionFactory factory=new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("guest");
            factory.setPassword("guest");
            try(Connection connection= factory.newConnection();
                Channel channel= connection.createChannel()){
                channel.queueDeclare(QUEUE_NAME,false,false,false,null);
                String message ="Hello RabbitMQ!";
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
                System.out.println("Message sent successfully.");
            }
            }catch (Exception e){
                e.printStackTrace();
        }
    }

}
