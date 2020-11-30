package com.northpole.remotesecretsanta.service;

import com.northpole.remotesecretsanta.config.ValidationProperties;
import com.northpole.remotesecretsanta.domain.SecretSanta;
import com.northpole.remotesecretsanta.repository.SecretSantaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecretSantaServiceTest {

  private static final UUID SECRET_SANTA_ID = UUID.randomUUID();

  @Mock
  private SecretSantaRepository secretSantaRepository;
  @Mock
  private ValidationProperties validationProperties;
  @Mock
  private SecretSanta secretSanta;

  private SecretSantaService secretSantaService;

  @BeforeEach
  void setUp() {
    secretSantaService = new SecretSantaService(secretSantaRepository, validationProperties);
  }

  @Test
  void run() {
    when(secretSantaRepository.findById(SECRET_SANTA_ID)).thenReturn(Optional.of(secretSanta));
    when(secretSantaRepository.save(secretSanta)).thenReturn(secretSanta);
    assertEquals(secretSantaService.run(SECRET_SANTA_ID), secretSanta);
  }

  @Test
  void run_throwsExceptionWhenSecretSantaCannotBeFound() {
    assertThrows(ResourceNotFoundException.class, () -> secretSantaService.run(SECRET_SANTA_ID));
  }

  @Test
  void run_callsRunOnSecretSanta() {
    when(secretSantaRepository.findById(SECRET_SANTA_ID)).thenReturn(Optional.of(secretSanta));
    secretSantaService.run(SECRET_SANTA_ID);
    verify(secretSanta).run(validationProperties);
  }
}