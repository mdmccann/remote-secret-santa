package com.northpole.remotesecretsanta.validation;

import com.northpole.remotesecretsanta.domain.SecretSanta;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.util.Objects.isNull;

public class SecretSantaValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return SecretSanta.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    SecretSanta secretSanta = (SecretSanta) o;

    if (isNull(secretSanta.getMaxGiftCost()) || BigDecimal.ZERO.compareTo(secretSanta.getMaxGiftCost()) >= 0) {
      errors.rejectValue("maxGiftCost", "secretSanta.maxGiftCost.not.valid");
    }

    if (isNull(secretSanta.getGiftDispatchDeadline()) || secretSanta.getGiftDispatchDeadline().isBefore(LocalDate.now())) {
      errors.rejectValue("giftDispatchDeadline", "secretSanta.giftDispatchDeadline.not.valid");
    }

    if (isNull(secretSanta.getMaxPartyMembers()) || secretSanta.getMaxPartyMembers() < 3) {
      errors.rejectValue("maxPartyMembers", "secretSanta.maxPartyMembers.not.valid");
    }
  }

}
