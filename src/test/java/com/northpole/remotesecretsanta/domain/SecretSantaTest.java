package com.northpole.remotesecretsanta.domain;

import com.northpole.remotesecretsanta.config.ValidationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecretSantaTest {

  @Mock
  private ValidationProperties validationProperties;

  private SecretSanta secretSanta;

  @BeforeEach
  void setUp() {
    secretSanta = createSecretSanta();
  }

  @Test
  void run_throwsExceptionWhenPartyMemberCountIsBelowMin() {
    when(validationProperties.getMinPartyMembers()).thenReturn(4);
    assertThrows(UnsupportedOperationException.class, () -> secretSanta.run(validationProperties));
  }

  @Test
  void run_shouldRandomlyAssignAPartyMemberToEachPartyMember() {
    when(validationProperties.getMinPartyMembers()).thenReturn(3);
    secretSanta.run(validationProperties);
    List<PartyMember> recipients = secretSanta.getPartyMembers().stream()
        .map(PartyMember::getRecipient)
        .collect(Collectors.toList());

    assertThat(recipients, containsInAnyOrder(secretSanta.getPartyMembers().get(0), secretSanta.getPartyMembers().get(1), secretSanta.getPartyMembers().get(2))); // TODO wtf?
  }

  @Test
  void run_doesNotAssignSelfAsRecipient() {
    when(validationProperties.getMinPartyMembers()).thenReturn(3);
    secretSanta.run(validationProperties);
    List<PartyMember> partyMembersWithSelfAsRecipient = secretSanta.getPartyMembers().stream()
        .filter(pm -> pm.equals(pm.getRecipient()))
        .collect(Collectors.toList());

    assertThat(partyMembersWithSelfAsRecipient, empty());
  }

  @Test
  void run_marksStatusAsComplete() {
    when(validationProperties.getMinPartyMembers()).thenReturn(3);
    secretSanta.run(validationProperties);
    assertEquals(secretSanta.getStatus(), SecretSantaStatus.COMPLETE);
  }

  private SecretSanta createSecretSanta() {
    SecretSanta secretSanta = new SecretSanta();
    secretSanta.setMaxGiftCost(BigDecimal.TEN);
    secretSanta.setGiftDispatchDeadline(LocalDate.of(2020, 12, 23));
    secretSanta.setMaxPartyMembers(5);

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
    return secretSanta;
  }
}