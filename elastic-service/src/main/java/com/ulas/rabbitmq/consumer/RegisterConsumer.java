package com.ulas.rabbitmq.consumer;

import com.ulas.rabbitmq.model.RegisterElasticModel;
import com.ulas.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j // console a log info çıktısı vermek için kullanılan kütüphane
public class RegisterConsumer {
    private final UserProfileService userProfileService;
    @RabbitListener(queues = ("${rabbitmq.queueRegisterElastic}"))
    public void newUserCreate(RegisterElasticModel model){
        log.info("User {}",model.toString());
        userProfileService.createUserWithRabbitMq(model);

    }
}
