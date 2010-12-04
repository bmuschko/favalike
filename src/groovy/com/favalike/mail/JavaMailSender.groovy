package com.favalike.mail

import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class JavaMailSender implements MailSender {
    static final Log log = LogFactory.getLog(JavaMailSender)

    def void sendMail(String from, String to, String subject, String text) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(text, "text/html; charset=UTF-8");
            Transport.send(message);

            log.debug("Successfully sent mail to '${to}' for subject '${subject}'")
        }
        catch(AddressException e) {
            log.fatal("Email address '${from}' or '${to}' is not a wrongly formatted address", e)
        }
        catch(MessagingException e) {
            log.fatal("There was a problem sending an email to '${to}'", e)
        }
    }
}
