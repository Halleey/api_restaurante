package card.cardapio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;
    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarEmail(String destinatario, String assunto, String mensagem) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject(assunto);
            simpleMailMessage.setText(mensagem);
            sender.send(simpleMailMessage);
            return "email enviado";
        }
        catch (Exception e) {
            return "erro ao enviar o email" + e.getLocalizedMessage();
        }
    }


}
