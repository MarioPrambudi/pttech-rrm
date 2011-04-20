package com.djavafactory.pttech.rrm.conf;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SingleConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Configuration class for RabbitMQ
 * @author Carine Leong
 */
@Configuration
@ImportResource("classpath:/META-INF/spring/applicationContext-si-request.xml")
public class AmqpConfiguration {
    @Value("#{amqp['broker.url']}")
    private String brokerUrl;

    @Value("#{amqp['broker.username']}")
    private String username;

    @Value("#{amqp['broker.password']}")
    private String password;

    @Value("#{amqp['queue.rmi.reload.status']}")
    private String rmiReloadStatusQ;

    @Value("#{amqp['queue.rrm.reload.req']}")
    private String rrmReloadReqQ;

    @Value("#{amqp['queue.rrm.reload.status']}")
    private String rrmReloadStatusQ;

    @Value("#{amqp['queue.rrm.terminal']}")
    private String rrmTerminalQ;

    @Value("#{amqp['queue.rrm.event']}")
    private String rrmEventQ;

    @Value("#{amqp['queue.rtm.reload.req']}")
    private String rtmReloadReqQ;

    @Value("#{amqp['queue.rtm.reload.status']}")
    private String rtmReloadStatusQ;

    @Value("#{amqp['queue.rtm.terminal']}")
    private String rtmTerminalQ;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(singleConnectionFactory());
	    return rabbitTemplate;
    }

    @Bean
    public RabbitTransactionManager rabbitTransactionManager() {
        return new RabbitTransactionManager(this.singleConnectionFactory());
    }

    @Bean
    public ConnectionFactory singleConnectionFactory() {
        SingleConnectionFactory connectionFactory = new SingleConnectionFactory(this.brokerUrl);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(this.rabbitTemplate());
    }

    @Bean
    public Queue rmiReloadStatusQueue() {
        Queue q = new Queue(this.rmiReloadStatusQ);
        amqpAdmin().declareQueue(q);
        return q;
    }

    @Bean
    public Queue rrmReloadReqQueue() {
        Queue q = new Queue(this.rrmReloadReqQ);
        amqpAdmin().declareQueue(q);
        return q;
    }

    @Bean
    public Queue rrmReloadStatusQueue() {
        Queue q = new Queue(this.rrmReloadStatusQ);
        amqpAdmin().declareQueue(q);
        return q;
    }

    @Bean
    public Queue rrmTerminalQueue() {
        Queue q = new Queue(this.rrmTerminalQ);
        amqpAdmin().declareQueue(q);
        return q;
    }

    @Bean
    public Queue rrmEventQueue() {
        Queue q = new Queue(this.rrmEventQ);
        amqpAdmin().declareQueue(q);
        return q;
    }

    @Bean
    public Queue rtmReloadReqQueue() {
        Queue q = new Queue(this.rtmReloadReqQ);
        amqpAdmin().declareQueue(q);
        return q;
    }

    @Bean
    public Queue rtmReloadStatusQueue() {
        Queue q = new Queue(this.rtmReloadStatusQ);
        amqpAdmin().declareQueue(q);
        return q;
    }

    @Bean
    public Queue rtmTerminalQueue() {
        Queue q = new Queue(this.rtmTerminalQ);
        amqpAdmin().declareQueue(q);
        return q;
    }

    @Bean
    public DirectExchange rmiReloadStatusExchange() {
        DirectExchange directExchange = new DirectExchange(this.rmiReloadStatusQ);
        this.amqpAdmin().declareExchange(directExchange);
        return directExchange ;
    }

    @Bean
    public DirectExchange rrmReloadReqExchange() {
        DirectExchange directExchange = new DirectExchange(this.rrmReloadReqQ);
        this.amqpAdmin().declareExchange(directExchange);
        return directExchange ;
    }

    @Bean
    public DirectExchange rrmReloadStatusExchange() {
        DirectExchange directExchange = new DirectExchange(this.rrmReloadStatusQ);
        this.amqpAdmin().declareExchange(directExchange);
        return directExchange ;
    }

    @Bean
    public DirectExchange rrmTerminalExchange() {
        DirectExchange directExchange = new DirectExchange(this.rrmTerminalQ);
        this.amqpAdmin().declareExchange(directExchange);
        return directExchange ;
    }

    @Bean
    public DirectExchange rrmEventExchange() {
        DirectExchange directExchange = new DirectExchange(this.rrmEventQ);
        this.amqpAdmin().declareExchange(directExchange);
        return directExchange ;
    }

    @Bean
    public DirectExchange rtmReloadReqExchange() {
        DirectExchange directExchange = new DirectExchange(this.rtmReloadReqQ);
        this.amqpAdmin().declareExchange(directExchange);
        return directExchange ;
    }

    @Bean
    public DirectExchange rtmReloadStatusExchange() {
        DirectExchange directExchange = new DirectExchange(this.rtmReloadStatusQ);
        this.amqpAdmin().declareExchange(directExchange);
        return directExchange ;
    }

    @Bean
    public DirectExchange rtmTerminalExchange() {
        DirectExchange directExchange = new DirectExchange(this.rtmTerminalQ);
        this.amqpAdmin().declareExchange(directExchange);
        return directExchange ;
    }

    @Bean
    public Binding rmiReloadStatusDataBinding() {
        return BindingBuilder.from(rmiReloadStatusQueue()).to(rmiReloadStatusExchange()).with(this.rmiReloadStatusQ);
    }

    @Bean
    public Binding rrmReloadRequestDataBinding() {
        return BindingBuilder.from(rrmReloadReqQueue()).to(rrmReloadReqExchange()).with(this.rrmReloadReqQ);
    }

    @Bean
    public Binding rrmReloadStatusDataBinding() {
        return BindingBuilder.from(rrmReloadStatusQueue()).to(rrmReloadStatusExchange()).with(this.rrmReloadStatusQ);
    }

    @Bean
    public Binding rrmTerminalDataBinding() {
        return BindingBuilder.from(rrmTerminalQueue()).to(rrmTerminalExchange()).with(this.rrmTerminalQ);
    }

    @Bean
    public Binding rrmEventBinding() {
        return BindingBuilder.from(rrmEventQueue()).to(rrmEventExchange()).with(this.rrmEventQ);
    }

    @Bean
    public Binding rtmReloadRequestDataBinding() {
        return BindingBuilder.from(rtmReloadReqQueue()).to(rtmReloadReqExchange()).with(this.rtmReloadReqQ);
    }

    @Bean
    public Binding rtmReloadStatusDataBinding() {
        return BindingBuilder.from(rtmReloadStatusQueue()).to(rtmReloadStatusExchange()).with(this.rtmReloadStatusQ);
    }

    @Bean
    public Binding rtmTerminalDataBinding() {
        return BindingBuilder.from(rtmTerminalQueue()).to(rtmTerminalExchange()).with(this.rtmTerminalQ);
    }

    @Override
    public String toString() {
        return "AmqpConfiguration{" +
                "brokerUrl='" + brokerUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rmiReloadStatusQ='" + rmiReloadStatusQ + '\'' +
                ", rrmReloadReqQ='" + rrmReloadReqQ + '\'' +
                ", rrmReloadStatusQ='" + rrmReloadStatusQ + '\'' +
                ", rrmTerminalQ='" + rrmTerminalQ + '\'' +
                ", rrmEventQ='" + rrmEventQ + '\'' +
                ", rtmReloadReqQ='" + rtmReloadReqQ + '\'' +
                ", rtmReloadStatusQ='" + rtmReloadStatusQ + '\'' +
                ", rtmTerminalQ='" + rtmTerminalQ + '\'' +
                '}';
    }
}
