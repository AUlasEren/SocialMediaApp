package com.ulas.service;

import com.ulas.rabbitmq.model.RegisterMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    public void sendMail(RegisterMailModel model){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${socialmediamailusername}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("AKTİVASYON KODU");
        mailMessage.setText(model.getUsername()+" adıyla başarılı bir şekilde kayıt oldunuz");
        mailMessage.setText("Aktivasyon kodunuz"+ model.getActivationCode());
        javaMailSender.send(mailMessage);
    }
}
