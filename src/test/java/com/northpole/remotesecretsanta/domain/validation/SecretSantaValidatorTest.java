package com.northpole.remotesecretsanta.domain.validation;

import com.northpole.remotesecretsanta.config.ValidationProperties;
import com.northpole.remotesecretsanta.domain.SecretSanta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecretSantaValidatorTest {

  private static final BigDecimal MIN_GIFT_COST = BigDecimal.ZERO;
  private static final Integer MIN_PARTY_MEMBERS = 3;

  @Mock
  private ValidationProperties validationProperties;
  @Mock
  private Errors errors;
  @Mock
  private SecretSanta secretSanta;

  private SecretSantaValidator validator;

  @BeforeEach
  void setUp() {
    validator = new SecretSantaValidator(validationProperties);
  }

  @Test
  void supports() {
    assertTrue(validator.supports(SecretSanta.class));
  }

  @Test
  void validate_valid() {
    when(validationProperties.getMinGiftCost()).thenReturn(MIN_GIFT_COST);
    when(validationProperties.getMinPartyMembers()).thenReturn(MIN_PARTY_MEMBERS);
    when(secretSanta.getMaxGiftCost()).thenReturn(BigDecimal.ZERO);
    when(secretSanta.getGiftDispatchDeadline()).thenReturn(LocalDate.now());
    when(secretSanta.getMaxPartyMembers()).thenReturn(3);
    validator.validate(secretSanta, errors);
    verify(errors, never()).rejectValue(any(), any());
  }

  @Test
  void validate_maxGiftCost_null() {
    when(validationProperties.getMinGiftCost()).thenReturn(MIN_GIFT_COST);
    validator.validate(secretSanta, errors);
    verify(errors).rejectValue(
        "maxGiftCost",
        "secretSanta.maxGiftCost.not.valid",
        new Object[]{MIN_GIFT_COST},
        "Max Gift Cost is invalid."
    );
  }

  @Test
  void validate_maxGiftCost_belowMinimumAllowed() {
    when(validationProperties.getMinGiftCost()).thenReturn(MIN_GIFT_COST);
    when(secretSanta.getMaxGiftCost()).thenReturn(BigDecimal.valueOf(-1));
    validator.validate(secretSanta, errors);
    verify(errors).rejectValue(
        "maxGiftCost",
        "secretSanta.maxGiftCost.not.valid",
        new Object[]{MIN_GIFT_COST},
        "Max Gift Cost is invalid."
    );
  }

  @Test
  void validate_giftDispatchDeadline_null() {
    validator.validate(secretSanta, errors);
    verify(errors).rejectValue("giftDispatchDeadline", "secretSanta.giftDispatchDeadline.not.valid");
  }

  @Test
  void validate_giftDispatchDeadline_inPast() {
    when(secretSanta.getGiftDispatchDeadline()).thenReturn(LocalDate.now().minusDays(1));
    validator.validate(secretSanta, errors);
    verify(errors).rejectValue("giftDispatchDeadline", "secretSanta.giftDispatchDeadline.not.valid");
  }

  @Test
  void validate_maxPartyNumbers_null() {
    when(validationProperties.getMinPartyMembers()).thenReturn(MIN_PARTY_MEMBERS);
    validator.validate(secretSanta, errors);
    verify(errors).rejectValue("maxPartyMembers",
        "secretSanta.maxPartyMembers.not.valid",
        new Object[]{MIN_PARTY_MEMBERS},
        "Minimum Party Members is invalid.");
  }

  @Test
  void validate_maxPartyNumbers_lessThanMinimumAllowed() {
    when(validationProperties.getMinPartyMembers()).thenReturn(MIN_PARTY_MEMBERS);
    when(secretSanta.getMaxPartyMembers()).thenReturn(2);
    validator.validate(secretSanta, errors);
    verify(errors).rejectValue(
        "maxPartyMembers",
        "secretSanta.maxPartyMembers.not.valid",
        new Object[]{MIN_PARTY_MEMBERS},
        "Minimum Party Members is invalid.");
  }

}