package com.northpole.remotesecretsanta.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class PartyMember {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String name;
  private String emailAddress;
  @Embedded
  private Address address;
  @ManyToOne
  @JoinColumn(name = "secret_santa_id")
  private SecretSanta secretSanta;

}
