package com.encora.easysat;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EasysatApplication {
	@Value("${amqp.queue.name.request}")
	private String requestQueueName;

	@Value("${amqp.queue.name.response}")
	private String responseQueueName;

	@Value("${amqp.exchange.name}")
	private String exchangeName;

	@Bean
	public Queue requestQueue() {
		return new Queue(requestQueueName, false);
	}

	@Bean
	public Queue responseQueue() {
		return new Queue(responseQueueName, false);
	}

//	@Bean
//	public TopicExchange exchange() {
//		return new TopicExchange(exchangeName);
//	}
//
//	@Bean
//	public Binding requestBinding(Queue requestQueue, TopicExchange exchange) {
//		return BindingBuilder.bind(requestQueue).to(exchange).with(requestQueueName);
//	}
//
//	@Bean
//	public Binding responseBinding(Queue responseQueue, TopicExchange exchange) {
//		return BindingBuilder.bind(responseQueue).to(exchange).with(responseQueueName);
//	}

	public static void main(String[] args) {
		SpringApplication.run(EasysatApplication.class, args);
	}

}
