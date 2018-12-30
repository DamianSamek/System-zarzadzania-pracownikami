package ur.edu.pl.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService{


    private JavaMailSender javaMailSender;

    public EmailService(){}

    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification(String to,String subject, String Text){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("ntitest8@gmail.com");
        mail.setSubject(subject);
        mail.setText(Text);

        javaMailSender.send(mail);
    }

}