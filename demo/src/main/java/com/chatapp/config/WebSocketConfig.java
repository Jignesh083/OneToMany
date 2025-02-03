package com.chatapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // This annotation indicates that this class is the source of bean definitions for the Spring container.
@EnableWebSocketMessageBroker // This annotation enables WebSocket message handling, which is supported by the message broker. This configures your application to use WebSocket and SockJS.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//	This method registers Stomp (Simple Text Oriented Messaging Protocol) endpoints.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // Which clients will use to connect to the WebSocket server.
                .setAllowedOriginPatterns("*") //Allows all origins to connect to this endpoint
                .withSockJS(); // SockJS enables fallback options for browsers that do not support WebSocket. SockJS is a WebSocket emulation library that provides a WebSocket-like API.
    }

    
//    This method is used to configure message broker options.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); //This enables an in-memory message broker that delivers messages to clients whose destinations have a prefix/topic. Like messages sent on /topic/chat are sent to all subscribers.
        registry.setApplicationDestinationPrefixes("/app"); //This sets the prefix for message mappings. Messages sent from clients to /app/someDestination will be routed to methods annotated with @MessageMapping in your controllers.
    }
}
