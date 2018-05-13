package com.sagarandcompany.RabbitMQPubSubExample.service;

import com.sagarandcompany.RabbitMQPubSubExample.RabbitMqPubSubExampleApplication;
import com.sagarandcompany.RabbitMQPubSubExample.listener.ProductMessageListener;
import com.sagarandcompany.RabbitMQPubSubExample.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    ProductMessageListener productMessageListener;

    public void publish(User user) {
        Map<String, User> actionmap = new HashMap<>();
        actionmap.put("user", user);
        rabbitTemplate.convertAndSend(RabbitMqPubSubExampleApplication.SFG_MESSAGE_QUEUE, actionmap);

    }

    public List<User> getList() {
        List<User> users = new ArrayList<>();
        users.add(productMessageListener.getUserMap().get("user"));
        return users;
    }
}
