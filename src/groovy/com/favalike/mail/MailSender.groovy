package com.favalike.mail

interface MailSender {
    void sendMail(String from, String to, String subject, String text); 
}