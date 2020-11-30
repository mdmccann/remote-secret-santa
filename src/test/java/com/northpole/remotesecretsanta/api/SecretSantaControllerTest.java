package com.northpole.remotesecretsanta.api;

import com.northpole.remotesecretsanta.config.ValidationProperties;
import com.northpole.remotesecretsanta.domain.PartyMember;
import com.northpole.remotesecretsanta.domain.SecretSanta;
import com.northpole.remotesecretsanta.domain.SecretSantaStatus;
import com.northpole.remotesecretsanta.service.SecretSantaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecretSantaControllerTest {

  private static final UUID SECRET_SANTA_ID = UUID.randomUUID();
  private static final String SECRET_SANTA_SELF_HREF = "http://remote-secret-santa.com/secretSantas/1";

  @Mock
  private SecretSantaService secretSantaService;
  @Mock
  private ValidationProperties validationProperties;
  @Mock
  private SecretSanta secretSanta;
  @Mock
  private PartyMember partyMember;

  private SecretSantaController controller;

  @BeforeEach
  void setUp() {
    controller = new SecretSantaController(secretSantaService, validationProperties);
  }

  @Test
  void run() {
    when(secretSantaService.run(SECRET_SANTA_ID)).thenReturn(secretSanta);
    assertEquals(controller.run(SECRET_SANTA_ID), ResponseEntity.ok(secretSanta));
  }

  @Test
  void process() {
    EntityModel<SecretSanta> entityModel = EntityModel.of(secretSanta);
    entityModel.add(Link.of(SECRET_SANTA_SELF_HREF));
    when(secretSanta.getStatus()).thenReturn(SecretSantaStatus.CREATED);
    when(secretSanta.getPartyMembers()).thenReturn(Collections.singletonList(partyMember));
    when(validationProperties.getMinPartyMembers()).thenReturn(1);
    assertEquals(controller.process(entityModel).getLink("run").get().getHref(), SECRET_SANTA_SELF_HREF + "/run");
  }
}