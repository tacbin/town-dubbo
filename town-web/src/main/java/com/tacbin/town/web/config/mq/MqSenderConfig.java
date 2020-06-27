package com.tacbin.town.web.config.mq;

import com.tacbin.town.common.constants.MqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MqSenderConfig {

    @Bean
    public Queue productOrderQueue() {
        return new Queue(MqConstants.PRODUCT_ORDER_QUEUE);
    }

    @Bean
    DirectExchange productOrderExchange() {
        return new DirectExchange(MqConstants.PRODUCT_ORDER_EXCHANGE);
    }

    @Bean
    Binding bindingOrderExchange(Queue AMessage, DirectExchange exchange) {
        return BindingBuilder.bind(AMessage).to(exchange).with(MqConstants.PRODUCT_ORDER_ROUTE_KEY);
    }
}
