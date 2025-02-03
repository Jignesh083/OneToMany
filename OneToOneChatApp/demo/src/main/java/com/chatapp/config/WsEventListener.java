package com.chatapp.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.chatapp.model.WsChatMessage;
import com.chatapp.model.WsChatMessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
 
@Component // The annotation allows Spring to automatically manage the class by registering it in the Spring context.
@RequiredArgsConstructor
@Slf4j


public class WsEventListener 
{
    private static final Logger log = LoggerFactory.getLogger(WsEventListener.class);  // Use LoggerFactory directly
 
// SimpMessageSendingOperations --> This class is used to send messages over WebSocket, such as sending a message in a public chat.
	
    private final SimpMessageSendingOperations messageSendingOperations;
	public WsEventListener(SimpMessageSendingOperations messageSendingOperations) 
	{
//		The dependency, injected via the constructor, is responsible for sending messages over WebSocket.
        this.messageSendingOperations = messageSendingOperations;
    }
	@EventListener
//	It listens to Spring events, and in this case, it listens for the disconnect event.
	public void handleWsDisconnectListener(SessionDisconnectEvent event)
	{
//		StompHeaderAccessor --> It provides access to the headers and session details of STOMP messages.
		
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage()); // It provides access to the header of the event message.
		String username = (String) headerAccessor .getSessionAttributes().get("username"); // The method retrieves the username from the user's session, set during the connection.
		if(username != null)
		{
			log.info("User disconnected: {} ", username);
			var message = WsChatMessage.builder()
						  .type(WsChatMessageType.LEAVE) // A message is created with the type "LEAVE" and the sender's username.
						  .sender(username)
						  .build();
			
			messageSendingOperations.convertAndSend("/topic/public", message); // The message is sent to the /topic/public topic using messageSendingOperations.convertAndSend() to notify other users that a user has disconnected.
		}
	}
}