package com.chatapp.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
 
@Configuration
@EnableWebSocketMessageBroker
public class Config implements WebSocketMessageBrokerConfigurer
{
	@Override
	//The method we use to define a WebSocket endpoint. These endpoints are the URLs that clients will connect to.
	public void registerStompEndpoints(StompEndpointRegistry registry)
	{
		registry.addEndpoint("/ws") // We create a WebSocket endpoint at /ws, which is the URL the client connects to
//				.setAllowedOriginPatterns("http://localhost:4200")
				.setAllowedOriginPatterns("*") // Allow all origins for development
				.withSockJS(); //SockJS is a fallback mechanism that is automatically used when WebSocket support is not available.
	}
	
	
	
	@Override
//	we configure the message broker that will route messages between clients.
	public void configureMessageBroker(MessageBrokerRegistry registry) 
	{
		registry.setApplicationDestinationPrefixes("/app"); //  The /app prefix is used to route client messages to the controller
		registry.enableSimpleBroker("/user"); // The message broker forwards messages to subscribed clients, and the /user prefix is used for client-specific or private messages.		
	}
}
 
 
 
 