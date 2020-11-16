package com.northpole.remotesecretsanta.validation;

import com.northpole.remotesecretsanta.domain.Address;
import com.northpole.remotesecretsanta.domain.PartyMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartyMemberValidatorTest {

  @Mock
  private Errors errors;
  @Mock
  private PartyMember partyMember;
  @Mock
  private Address address;

  private PartyMemberValidator validator;

  @BeforeEach
  void setUp() {
    validator = new PartyMemberValidator();
  }

  @Test
  void supports() {
    assertTrue(validator.supports(PartyMember.class));
  }

  @Test
  void validate_valid() {
    when(partyMember.getName()).thenReturn("John Smith");
    when(partyMember.getAddress()).thenReturn(address);
    when(partyMember.getEmailAddress()).thenReturn("test@gmail.com");
    when(address.getLine1()).thenReturn("line1");
    when(address.getCity()).thenReturn("city");
    when(address.getPostcode()).thenReturn("postcode");
    validator.validate(partyMember, errors);
    verify(errors, never()).rejectValue(any(), any());
  }

  @Test
  void validate_name_null() {
    validator.validate(partyMember, errors);
    verify(errors).rejectValue("name", "partyMember.name.missing");
  }

  @Test
  void validate_name_empty() {
    when(partyMember.getName()).thenReturn("");
    validator.validate(partyMember, errors);
    verify(errors).rejectValue("name", "partyMember.name.missing");
  }

  @Test
  void validate_emailAddress_null() {
    validator.validate(partyMember, errors);
    verify(errors).rejectValue("emailAddress", "partyMember.email.missing");
  }

  @Test
  void validate_emailAddress_empty() {
    when(partyMember.getEmailAddress()).thenReturn("");
    validator.validate(partyMember, errors);
    verify(errors).rejectValue("emailAddress", "partyMember.email.missing");
  }

  @Test
  void validate_address_null() {
    validator.validate(partyMember, errors);
    verify(errors).rejectValue("address", "partyMember.address.missing");
  }

  @Test
  void validate_address_line1_null() {
    when(partyMember.getAddress()).thenReturn(address);
    validator.validate(partyMember, errors);
    verify(errors).rejectValue("address.line1", "partyMember.address.line1.missing");
  }

  @Test
  void validate_address_line1_empty() {
    when(partyMember.getAddress()).thenReturn(address);
    when(address.getLine1()).thenReturn("");
    validator.validate(partyMember, errors);
    verify(errors).rejectValue("address.line1", "partyMember.address.line1.missing");
  }

  @Test
  void validate_address_city_null() {
    when(partyMember.getAddress()).thenReturn(address);
    validator.validate(partyMember, errors);
    verify(errors).rejectValue("address.city", "partyMember.address.city.missing");
  }

  @Test
  void validate_address_city_empty() {
    when(partyMember.getAddress()).thenReturn(address);
    when(address.getCity()).thenReturn("");
    validator.validate(partyMember, errors);
    verify(errors).rejectValue("address.city", "partyMember.address.city.missing");
  }

  @Test
  void validate_address_postcode_null() {
    when(partyMember.getAddress()).thenReturn(address);
    validator.validate(partyMember, errors);
    verify(errors).rejectValue("address.postcode", "partyMember.address.postcode.missing");
  }

  @Test
  void validate_address_postcode_empty() {
    when(partyMember.getAddress()).thenReturn(address);
    when(address.getPostcode()).thenReturn("");
    validator.validate(partyMember, errors);
    verify(errors).rejectValue("address.postcode", "partyMember.address.postcode.missing");
  }

}