
package com.example.enaula.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EmailService {

    private static final Logger log =
            LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String remetente;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmail(
            String destinatario,
            String assunto,
            String mensagem) {

        if (!StringUtils.hasText(destinatario)) {
            log.warn("E-mail não enviado: destinatário vazio.");
            return;
        }

        if (!StringUtils.hasText(remetente)) {
            log.warn(
                    "E-mail não enviado: configure MAIL_USERNAME e MAIL_PASSWORD."
            );
            return;
        }

        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(remetente);
        email.setTo(destinatario);
        email.setSubject(assunto);
        email.setText(mensagem);

        try {
            mailSender.send(email);
            log.info(
                    "E-mail de notificação enviado para {}",
                    destinatario
            );
        } catch (MailException e) {
            log.error(
                    "Falha ao enviar e-mail para {}",
                    destinatario,
                    e
            );
        }
    }
}