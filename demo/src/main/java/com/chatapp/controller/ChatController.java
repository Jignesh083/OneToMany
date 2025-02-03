package com.chatapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.chatapp.model.ChatMessage;

@Controller // It is used to handle web requests.
public class ChatController {

    @MessageMapping("/sendMessage") //This annotation maps incoming messages sent to the /sendMessage destination to the sendMessage method. It works similarly to @RequestMapping but for WebSocket messages.
    @SendTo("/topic/public") //This annotation indicates that the return value of the sendMessage method should be sent to the /topic/public destination. This means that the message will be broadcasted to all subscribers of the /topic/public topic.
    public ChatMessage sendMessage(ChatMessage message) {
        // This method handles incoming messages sent to the /sendMessage destination
        // The message is then broadcasted to the /topic/public destination
        return message;
    }
}