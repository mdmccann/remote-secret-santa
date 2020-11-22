package com.northpole.remotesecretsanta.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
@EqualsAndHashCode(exclude = "recipient")
@ToString(exclude = "recipient")
@Entity
public class PartyMember {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String name;
  private String emailAddress;
  @Embedded
  private Address address;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "secret_santa_id")
  private SecretSanta secretSanta;
  @OneToOne
  private PartyMember recipient;

}
