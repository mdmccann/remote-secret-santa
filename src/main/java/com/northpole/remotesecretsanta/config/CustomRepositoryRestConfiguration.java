package com.northpole.remotesecretsanta.config;

import com.northpole.remotesecretsanta.domain.validation.PartyMemberValidator;
import com.northpole.remotesecretsanta.domain.validation.SecretSantaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
@RequiredArgsConstructor
public class CustomRepositoryRestConfiguration implements RepositoryRestConfigurer {

  private final ValidationProperties validationProperties;

  @Override
  public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
    validatingListener.addValidator("beforeCreate", new SecretSantaValidator(validationProperties));
    validatingListener.addValidator("beforeSave", new SecretSantaValidator(validationProperties));
    validatingListener.addValidator("beforeCreate", new PartyMemberValidator());
    validatingListener.addValidator("beforeSave", new PartyMemberValidator());
  }

}