package com.northpole.remotesecretsanta.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class SecretSanta {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  @OneToMany(mappedBy = "secretSanta")
  private List<PartyMember> partyMembers;
  private BigDecimal maxGiftCost;
  private LocalDate giftDispatchDeadline;
  private Integer maxPartyMembers;

}
