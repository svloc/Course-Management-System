package com.cms.config;

import java.io.IOException;


import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

import com.rabbitmq.client.ConnectionFactory;
// import com.rabbitmq.client.Channel;
// import com.rabbitmq.client.Connection;
//  @Component
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




	public ConnectionFactory connectionFactory() throws IOException{ 
	   ConnectionFactory factory  = new ConnectionFactory();
	   factory.setHost(host);
	   factory.setPort(port);
	   factory.setUsername(userName);
	   factory.setPassword(password);
	   factory.setVirtualHost(virtualHost);
		return factory;
		
	}

	public Queue queue()
	{
		return null;
	}

	
	
	
	
	
}
