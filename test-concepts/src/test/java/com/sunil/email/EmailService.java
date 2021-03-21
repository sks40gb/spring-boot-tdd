package com.sunil.email;


public class EmailService{

    private Platform platform;

    public EmailService(Platform platform) {
        this.platform = platform;
    }

    public void sendEmail(String sendTo, String subject, String content, boolean html) {
        System.out.println("sending email to " + sendTo);
        Email email = new Email();
        email.setSendTo(sendTo);
        email.setSubject(subject);
        email.setContent(content);
        if (html) {
            email.setFormat(Format.HTML);
        } else {
            email.setFormat(Format.TEXT);
        }
        this.platform.sendEmail(email);
    }
}
