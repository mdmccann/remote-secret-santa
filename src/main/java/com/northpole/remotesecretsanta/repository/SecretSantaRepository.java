package com.northpole.remotesecretsanta.repository;

import com.northpole.remotesecretsanta.domain.SecretSanta;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SecretSantaRepository extends CrudRepository<SecretSanta, UUID> {
}
