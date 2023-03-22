package com.ulas.rabbitmq.producer;

import com.ulas.rabbitmq.model.RegisterMailModel;
import com.ulas.rabbitmq.model.RegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterMailProducer {
    @Value("${rabbitmq.exchange-auth}")
    private String directExchange;
    @Value("${rabbitmq.registerMailKey}")
    private String registerMailKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendNewMail(RegisterMailModel model){
        rabbitTemplate.convertAndSend(directExchange,registerMailKey,model);
    }
}
