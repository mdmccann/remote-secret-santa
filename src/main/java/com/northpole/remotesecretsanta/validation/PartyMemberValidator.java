package com.northpole.remotesecretsanta.validation;

import com.northpole.remotesecretsanta.domain.PartyMember;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

public class PartyMemberValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return PartyMember.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    PartyMember partyMember = (PartyMember) o;

    if (isNullOrEmpty(partyMember.getName())) {
      errors.rejectValue("name", "partyMember.name.missing");
    }

    if (isNullOrEmpty(partyMember.getEmailAddress())) {
      errors.rejectValue("emailAddress", "partyMember.email.missing");
    }

    if (isNull(partyMember.getAddress())) {
      errors.rejectValue("address", "partyMember.address.missing");
    } else {
      if (isNullOrEmpty(partyMember.getAddress().getLine1())) {
        errors.rejectValue("address.line1", "partyMember.address.line1.missing");
      }
      if (isNullOrEmpty(partyMember.getAddress().getCity())) {
        errors.rejectValue("address.city", "partyMember.address.city.missing");
      }
      if (isNullOrEmpty(partyMember.getAddress().getPostcode())) {
        errors.rejectValue("address.postcode", "partyMember.address.postcode.missing");
      }
    }
  }

  private boolean isNullOrEmpty(String str) {
    return isNull(str) || str.trim().equals("");
  }

}
