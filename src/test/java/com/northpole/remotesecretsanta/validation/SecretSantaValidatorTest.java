package com.northpole.remotesecretsanta.validation;

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

  @Mock
  private Errors errors;
  @Mock
  private SecretSanta secretSanta;

  private SecretSantaValidator validator;

  @BeforeEach
  void setUp() {
    validator = new SecretSantaValidator();
  }

  @Test
  void supports() {
    assertTrue(validator.supports(SecretSanta.class));
  }

  @Test
  void validate_valid() {
    when(secretSanta.getMaxGiftCost()).thenReturn(BigDecimal.TEN);
    when(secretSanta.getGiftDispatchDeadline()).thenReturn(LocalDate.now());
    when(secretSanta.getMaxPartyMembers()).thenReturn(3);
    validator.validate(secretSanta, errors);
    verify(errors, never()).rejectValue(any(), any());
  }

  @Test
  void validate_maxGiftCost_null() {
    validator.validate(secretSanta, errors);
    verify(errors).rejectValue("maxGiftCost", "secretSanta.maxGiftCost.not.valid");
  }

  @Test
  void validate_maxGiftCost_negative() {
    when(secretSanta.getMaxGiftCost()).thenReturn(BigDecimal.valueOf(-1));
    validator.validate(secretSanta, errors);
    verify(errors).rejectValue("maxGiftCost", "secretSanta.maxGiftCost.not.valid");
  }

  @Test
  void validate_maxGiftCost_zero() {
    when(secretSanta.getMaxGiftCost()).thenReturn(BigDecimal.ZERO);
    validator.validate(secretSanta, errors);
    verify(errors).rejectValue("maxGiftCost", "secretSanta.maxGiftCost.not.valid");
  }

  @Test
  void validate_maxGiftCost_valid() {
    when(secretSanta.getMaxGiftCost()).thenReturn(BigDecimal.ONE);
    validator.validate(secretSanta, errors);
    verify(errors, never()).rejectValue("maxGiftCost", "secretSanta.maxGiftCost.not.valid");
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
  void validate_giftDispatchDeadline_valid() {
    when(secretSanta.getGiftDispatchDeadline()).thenReturn(LocalDate.now().plusDays(10));
    validator.validate(secretSanta, errors);
    verify(errors, never()).rejectValue("giftDispatchDeadline", "secretSanta.giftDispatchDeadline.not.valid");
  }

  @Test
  void validate_maxPartyNumbers_null() {
    validator.validate(secretSanta, errors);
    verify(errors).rejectValue("maxPartyMembers", "secretSanta.maxPartyMembers.not.valid");
  }

  @Test
  void validate_maxPartyNumbers_lessThanThree() {
    when(secretSanta.getMaxPartyMembers()).thenReturn(2);
    validator.validate(secretSanta, errors);
    verify(errors).rejectValue("maxPartyMembers", "secretSanta.maxPartyMembers.not.valid");
  }

  @Test
  void validate_maxPartyNumbers_valid() {
    when(secretSanta.getMaxPartyMembers()).thenReturn(3);
    validator.validate(secretSanta, errors);
    verify(errors, never()).rejectValue("maxPartyMembers", "secretSanta.maxPartyMembers.not.valid");
  }

}