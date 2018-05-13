# Rabbitmq-RabbitMQPubSubExample
https://www.sagarandcompany.com/


# This is Example of RabbitMQ Pub Sub Example using Spring Boot
# Configuration:=>
```java
public class RabbitMqPubSubExampleApplication {
    public final static String SFG_MESSAGE_QUEUE = "sfg-message-queue";

    @Bean
    Queue queue() {
        return new Queue(SFG_MESSAGE_QUEUE, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(SFG_MESSAGE_QUEUE);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(SFG_MESSAGE_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ProductMessageListener receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
```


# Publisher->

If we want to publish a message into queue then we need RabbitTemplate class

```java
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

```

# Consumer

```java
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

```
