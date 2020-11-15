package com.northpole.remotesecretsanta.domain;

import lombok.Data;

@Data
public class Address {

  private String line1;
  private String line2;
  private String city;
  private String postCode;

}
