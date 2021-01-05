package com.northpole.remotesecretsanta.api;

import com.northpole.remotesecretsanta.domain.Address;
import com.northpole.remotesecretsanta.domain.PartyMember;
import com.northpole.remotesecretsanta.domain.SecretSanta;
import com.northpole.remotesecretsanta.domain.SecretSantaStatus;
import com.northpole.remotesecretsanta.repository.SecretSantaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class SecretSantaControllerIT extends IntegrationTest {

  @Autowired
  private SecretSantaController secretSantaController;
  @Autowired
  private SecretSantaRepository secretSantaRepository;

  @Test
  void run() {
    SecretSanta secretSanta = givenASecretSantaWithParticipants();
    SecretSanta ranSecretSanta = whenTheSecretSantaIsRan(secretSanta.getId());
    thenTheStatusIs(SecretSantaStatus.COMPLETE, ranSecretSanta);
  }

  @Test
  void run_sendsEmails() {
    SecretSanta secretSanta = givenASecretSantaWithParticipants();
    SecretSanta ranSecretSanta = whenTheSecretSantaIsRan(secretSanta.getId());
    thenAnEmailHasBeenSentForEachParticipant(ranSecretSanta);
  }

  private SecretSanta givenASecretSantaWithParticipants() {
    SecretSanta secretSanta = new SecretSanta();
    secretSanta.setMaxGiftCost(BigDecimal.TEN);
    secretSanta.setGiftDispatchDeadline(LocalDate.of(2020, 12, 23));
    secretSanta.setMaxPartyMembers(5);
    SecretSanta persistentSecretSanta = secretSantaRepository.save(secretSanta);

    PartyMember partyMember1 = new PartyMember();
    partyMember1.setName("Member One");
    partyMember1.setEmailAddress("member1@gmail.com");
    partyMember1.setAddress(new Address("Flat 1/2", "12 Snow Lane", "North Pole", "N1 3RT"));
    partyMember1.setSecretSanta(secretSanta);

    PartyMember partyMember2 = new PartyMember();
    partyMember2.setName("Member Two");
    partyMember2.setEmailAddress("member2@gmail.com");
    partyMember2.setAddress(new Address("11 Ice Drive", null, "North Pole", "N1 EYX"));
    partyMember2.setSecretSanta(secretSanta);

    PartyMember partyMember3 = new PartyMember();
    partyMember3.setName("Member Three");
    partyMember3.setEmailAddress("member3@gmail.com");
    partyMember3.setAddress(new Address("Igloo on The Hill", null, "Iceland", "I3 YRT"));
    partyMember3.setSecretSanta(secretSanta);

    secretSanta.setPartyMembers(Arrays.asList(partyMember1, partyMember2, partyMember3));
    return secretSantaRepository.save(persistentSecretSanta);
  }

  private SecretSanta whenTheSecretSantaIsRan(UUID secretSantaId) {
    return (SecretSanta) secretSantaController.run(secretSantaId).getBody();
  }

  private void thenTheStatusIs(SecretSantaStatus expectedStatus, SecretSanta secretSanta) {
    assertEquals(expectedStatus, secretSanta.getStatus());
  }

  private void thenAnEmailHasBeenSentForEachParticipant(SecretSanta secretSanta) {
    assertEquals(secretSanta.getPartyMembers().size(), getReceivedMailMessages().length);
  }

}