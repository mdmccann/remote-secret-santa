package com.northpole.remotesecretsanta.service;

import com.northpole.remotesecretsanta.config.ValidationProperties;
import com.northpole.remotesecretsanta.domain.SecretSanta;
import com.northpole.remotesecretsanta.repository.SecretSantaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecretSantaService {

  private final SecretSantaRepository secretSantaRepository;
  private final ValidationProperties validationProperties;

  public SecretSanta run(UUID secretSantaId) {
    SecretSanta secretSanta = secretSantaRepository.findById(secretSantaId)
        .orElseThrow(() -> new ResourceNotFoundException(String.format("Secret santa with id %s could not be found", secretSantaId)));

    secretSanta.run(validationProperties);
    // todo: Trigger recipient alerting

    return secretSantaRepository.save(secretSanta);
  }

}
