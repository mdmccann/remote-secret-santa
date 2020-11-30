package com.northpole.remotesecretsanta.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
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
