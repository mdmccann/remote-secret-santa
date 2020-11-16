package com.northpole.remotesecretsanta.config;

import com.northpole.remotesecretsanta.validation.PartyMemberValidator;
import com.northpole.remotesecretsanta.validation.SecretSantaValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class CustomRepositoryRestConfiguration implements RepositoryRestConfigurer {

  @Override
  public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
    validatingListener.addValidator("beforeCreate", new SecretSantaValidator());
    validatingListener.addValidator("beforeSave", new SecretSantaValidator());
    validatingListener.addValidator("beforeCreate", new PartyMemberValidator());
    validatingListener.addValidator("beforeSave", new PartyMemberValidator());
  }

}