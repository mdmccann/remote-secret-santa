package com.northpole.remotesecretsanta.domain.validation;

import com.northpole.remotesecretsanta.config.ValidationProperties;
import com.northpole.remotesecretsanta.domain.SecretSanta;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

import static java.util.Objects.isNull;

public class SecretSantaValidator implements Validator {

  private ValidationProperties validationProperties;

  public SecretSantaValidator(ValidationProperties validationProperties) {
    this.validationProperties = validationProperties;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return SecretSanta.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    SecretSanta secretSanta = (SecretSanta) o;

    if (isNull(secretSanta.getMaxGiftCost()) || validationProperties.getMinGiftCost().compareTo(secretSanta.getMaxGiftCost()) > 0) {
      errors.rejectValue("maxGiftCost", "secretSanta.maxGiftCost.not.valid",
          new Object[]{validationProperties.getMinGiftCost()}, "Max Gift Cost is invalid.");
    }

    if (isNull(secretSanta.getGiftDispatchDeadline()) || secretSanta.getGiftDispatchDeadline().isBefore(LocalDate.now())) {
      errors.rejectValue("giftDispatchDeadline", "secretSanta.giftDispatchDeadline.not.valid");
    }

    if (isNull(secretSanta.getMaxPartyMembers()) || validationProperties.getMinPartyMembers() > secretSanta.getMaxPartyMembers()) {
      errors.rejectValue("maxPartyMembers", "secretSanta.maxPartyMembers.not.valid",
          new Object[]{validationProperties.getMinPartyMembers()}, "Minimum Party Members is invalid.");
    }
  }

}
