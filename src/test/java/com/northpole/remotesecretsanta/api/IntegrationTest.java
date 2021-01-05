package com.northpole.remotesecretsanta.api;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.mail.internet.MimeMessage;

public abstract class IntegrationTest {

  private GreenMail greenMail;

  @BeforeEach
  void startGreenmailServer() {
    greenMail = new GreenMail(ServerSetupTest.SMTP);
    greenMail.setUser("username", "pw");
    greenMail.start();
  }

  @AfterEach
  void stopGreenmailServer() {
    greenMail.stop();
  }

  MimeMessage[] getReceivedMailMessages() {
    return greenMail.getReceivedMessages();
  }

}
