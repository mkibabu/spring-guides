package app;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import receiver.Receiver;

/*
 * Configures a message listener container, and declares the queue, exchange
 * and binding between them. Uses a RabbitTemplate to send messages, and
 * registers our Receiver with the message listener container to receive messages.
 * The registration container drives both, allowing them to connect to the rabbitMQ
 * server.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    final static String queueName = "spring-boot";

    @Autowired
    AnnotationConfigApplicationContext context;
    
    @Autowired
    RabbitTemplate rabbitTemplate;
    
    /**
     * Creates the message queue.
     * @return
     */
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }
    
    @Bean
    /**
     * Creates the AMPQ topic exchange.
     * @return
     */
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }
    
    /**
     * Binds the exchange to the queue.
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }
    
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    
    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Waiting five seconds...");
        Thread.sleep(5000);
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ!");
        receiver().getLatch().await(10000, TimeUnit.MILLISECONDS);
        context.close();
    }
}
