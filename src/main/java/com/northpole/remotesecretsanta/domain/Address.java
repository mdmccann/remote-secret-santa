package com.northpole.remotesecretsanta.domain;

import lombok.Data;

import javax.persistence.Column;

@Data
public class Address {

  @Column(name = "addressLine1")
  private String line1;
  @Column(name = "addressLine2")
  private String line2;
  @Column(name = "addressCity")
  private String city;
  @Column(name = "addressPostcode")
  private String postcode;

}
