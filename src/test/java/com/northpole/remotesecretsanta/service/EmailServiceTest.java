package com.northpole.remotesecretsanta.service;

import com.northpole.remotesecretsanta.domain.Address;
import com.northpole.remotesecretsanta.domain.PartyMember;
import com.northpole.remotesecretsanta.domain.SecretSanta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

  @Mock
  private JavaMailSender javaMailSender;
  @Mock
  private MimeMessage mimeMessage;
  @Mock
  private SecretSanta secretSanta;
  @Mock
  private PartyMember partyMember;
  @Mock
  private PartyMember partyMember2;
  @Mock
  private PartyMember recipient;
  @Mock
  private Address recipientAddress;

  private EmailService emailService;

  @BeforeEach
  void setUp() {
    emailService = new EmailService(javaMailSender);
  }

  @Test
  void notifyParticipants() {
    when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    when(secretSanta.getPartyMembers()).thenReturn(Arrays.asList(partyMember, partyMember2));
    when(partyMember.getEmailAddress()).thenReturn("partyMember@gmail.com");
    when(partyMember.getRecipient()).thenReturn(recipient);
    when(partyMember2.getEmailAddress()).thenReturn("partyMember@gmail.com");
    when(partyMember2.getRecipient()).thenReturn(recipient);
    when(recipient.getName()).thenReturn("recipientName");
    when(recipient.getAddress()).thenReturn(recipientAddress);
    when(recipientAddress.getLine1()).thenReturn("recipientLine1");
    when(recipientAddress.getLine2()).thenReturn("recipientLine2");
    when(recipientAddress.getCity()).thenReturn("recipientCity");
    when(recipientAddress.getPostcode()).thenReturn("E1 8GT");
    emailService.notifyParticipants(secretSanta);
    verify(javaMailSender, times(2)).send(mimeMessage);
  }

}