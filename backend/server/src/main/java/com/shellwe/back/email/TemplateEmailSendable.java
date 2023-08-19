package com.shellwe.back.email;

import com.shellwe.back.exception.customexception.EmailLogicException;
import com.shellwe.back.exception.exceptioncode.EmailExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
public class TemplateEmailSendable implements EmailSendable {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final Context context;

    public TemplateEmailSendable(JavaMailSender javaMailSender, TemplateEngine templateEngine, Context context) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.context = context;
    }

    @Override
    public void send(String[] to, String subject, String email, String templateName) {
        try {
            context.setVariable("email", email);
            context.setVariable("url", "http://ec2-13-209-106-52.ap-northeast-2.compute.amazonaws.com:8080");

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            String html = templateEngine.process(templateName, context);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(html, true);

            javaMailSender.send(mimeMessage);
            log.info("mail sent");
        } catch (MessagingException e) {
            throw new EmailLogicException(EmailExceptionCode.EMAIL_NOT_SEND);
        }
    }
}
