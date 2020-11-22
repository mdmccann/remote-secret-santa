package com.northpole.remotesecretsanta.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@ToString(exclude = "partyMembers")
@Entity
public class SecretSanta {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  @JsonIgnore
  @OneToMany(mappedBy = "secretSanta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<PartyMember> partyMembers;
  private BigDecimal maxGiftCost;
  private LocalDate giftDispatchDeadline;
  private Integer maxPartyMembers;

}
