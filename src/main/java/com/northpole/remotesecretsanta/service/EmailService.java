package com.northpole.remotesecretsanta.service;

import com.northpole.remotesecretsanta.domain.PartyMember;
import com.northpole.remotesecretsanta.domain.SecretSanta;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender javaMailSender;

  public void notifyParticipants(SecretSanta secretSanta) {
    secretSanta.getPartyMembers().stream()
        .map(this::getMimeMessage)
        .forEach(javaMailSender::send);
  }

  private MimeMessage getMimeMessage(PartyMember partyMember) {
    try {
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(partyMember.getEmailAddress()));
      mimeMessage.setSubject("You're the Secret Santa for...");

      StringBuilder messageBuilder = new StringBuilder()
          .append(String.format("You are the Secret Santa for %s, please find address below.", partyMember.getRecipient().getName()))
          .append("\n\n")
          .append(partyMember.getRecipient().getAddress().getLine1()).append("\n");

      if (nonNull(partyMember.getRecipient().getAddress().getLine2()) || !partyMember.getRecipient().getAddress().getLine2().trim().equals("")) {
        messageBuilder.append(partyMember.getRecipient().getAddress().getLine2()).append("\n");
      }

      messageBuilder
          .append(partyMember.getRecipient().getAddress().getCity()).append("\n")
          .append(partyMember.getRecipient().getAddress().getPostcode());

      mimeMessage.setText(messageBuilder.toString());

      return mimeMessage;

    } catch (MessagingException e) {
      throw new RuntimeException("Failed to send notification emails", e);
    }
  }

}
