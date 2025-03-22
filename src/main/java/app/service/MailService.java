package app.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.Map;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    
    public void sendEmail(String to, String subject, String body, String from) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to); // Destinatario
        helper.setSubject(subject); // Asunto
        helper.setText(body, true); // Cuerpo en HTML
        helper.setFrom(from); // Remitente (nombre y correo)

        mailSender.send(message);
    }
    public void sendEmailWithTemplate(String to, String subject, String templateName, Map<String, Object> variables, String from) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Procesar la plantilla
        Context context = new Context();
        context.setVariables(variables);
        String body = templateEngine.process(templateName, context);

        // Configurar el correo
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        helper.setFrom(from);
        
        // Adjuntar el logo como recurso embebido
        helper.addInline("liderLogo", new ClassPathResource("static/img/liderlogo.png"));

        mailSender.send(message);
    }
    
    public void sendEmailWithAttachment(String to, String subject, String templateName, 
            Map<String, Object> variables, String from, File attachment) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		
		// Procesar la plantilla
		Context context = new Context();
		context.setVariables(variables);
		String body = templateEngine.process(templateName, context);
		
		// Configurar el correo
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body, true);
		helper.setFrom(from);
		
		// Adjuntar el PDF
		helper.addAttachment("Certificado.pdf", attachment);
		
		// Adjuntar el logo
		helper.addInline("liderLogo", new ClassPathResource("static/img/liderlogo.png"));
		
		// Enviar el correo
		mailSender.send(message);
	}
    
    

}

