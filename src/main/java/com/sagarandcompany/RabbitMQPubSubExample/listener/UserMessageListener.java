package com.sagarandcompany.RabbitMQPubSubExample.listener;

import com.sagarandcompany.RabbitMQPubSubExample.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * This is the queue listener class, its receiveMessage() method ios invoked with the
 * message as the parameter.
 */
@Component
public class UserMessageListener {

    private static final Logger log = LogManager.getLogger(UserMessageListener.class);
    private Map<String, User> map = null;

    public void receiveMessage(Map<String, User> message) {
        log.info("Received <" + message + ">");
        log.info("Message processed...");
        this.map = message;
    }

    public Map<String, User> getUserMap() {
        return map;
    }
}
