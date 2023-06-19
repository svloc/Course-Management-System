package com.cms.config;

import java.io.IOException;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;




public class RabbitMQConfig
{
	@Value("${spring.rabbitmq.queue-name}")
	private String queueName;
	
	@Value("${spring.rabbitmq.host}")
	private String host;
	
	@Value("${spring.rabbitmq.port}")
	private int port;
	
	@Value("${spring.rabbitmq.username}")
	private String userName;
	
	@Value("${spring.rabbitmq.password}")
	private String password;
	
	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost;




	public ConnectionFactory connectionFactory() throws IOException
	{
		return null;
	}

	public Queue queue()
	{
		return null;
	}

	
	
	
	
	
}
