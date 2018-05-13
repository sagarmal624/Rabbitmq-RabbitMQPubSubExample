# Rabbitmq-RabbitMQPubSubExample
https://www.sagarandcompany.com/


# Basic Questions about RabbitMQ

```java
Q: What is RabbitMQ ?
A:RabbitMQ is an open source message broker software (sometimes called message-oriented middleware) that implements the Advanced Message Queuing Protocol (AMQP). The RabbitMQ server is written in the Erlang programming language and is built on the Open Telecom Platform framework for clustering and failover.

Q: What is an exchange in RabbitMQ?
A: An exchange accepts messages from the producer application and routes them to message queues with help of header attributes, bindings, and routing keys. A binding is a "link" that you set up to bind a queue to an exchange.
Q: What is routing key in RabbitMQ?
A: The routing key is a message attribute. The routing algorithm behind a direct exchange is simple - a message goes to the queues whose binding key exactly matches the routing key of the message. 
Q: What is Erlang ? Why is it required for RabbitMQ ?
A: Erlang is a general-purpose, concurrent, functional programming language, as well as a garbage-collected runtime system. The RabbitMQ server is written in the Erlang programming language and is built on the Open Telecom Platform framework for clustering and failover. Since RabbitMQ is built on top of Erlang, we will first need to install Erlang beforing installing RabbitMQ
```


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
    MessageListenerAdapter listenerAdapter(UserMessageListener receiver) {
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

