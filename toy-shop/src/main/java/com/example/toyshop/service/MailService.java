package com.example.toyshop.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendFindIdMail(String email, String loginId) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("zzangmait@naver.com");
        message.setTo(email);
        message.setSubject("[TOY-SHOP] 아이디 찾기 안내");
        message.setText(
                "안녕하세요. TOY-SHOP입니다.\n\n" +
                        "회원님의 아이디는 아래와 같습니다.\n\n" +
                        "아이디 : " + loginId + "\n\n" +
                        "감사합니다."
        );

        mailSender.send(message);
    }

    public void sendTempPasswordMail(String email, String tempPassword) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("zzangmait@naver.com");
        message.setTo(email);
        message.setSubject("[TOY-SHOP] 임시 비밀번호 안내");
        message.setText(
                "안녕하세요. TOY-SHOP입니다.\n\n" +
                        "임시 비밀번호가 발급되었습니다.\n\n" +
                        "임시 비밀번호 : " + tempPassword + "\n\n" +
                        "로그인 후 반드시 비밀번호를 변경해주세요.\n\n" +
                        "감사합니다."
        );

        mailSender.send(message);
    }
}