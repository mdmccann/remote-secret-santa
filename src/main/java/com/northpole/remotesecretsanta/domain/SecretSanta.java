package com.northpole.remotesecretsanta.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.northpole.remotesecretsanta.config.ValidationProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
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
  @OneToMany(mappedBy = "secretSanta", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PartyMember> partyMembers;
  private BigDecimal maxGiftCost;
  private LocalDate giftDispatchDeadline;
  private Integer maxPartyMembers;
  private SecretSantaStatus status = SecretSantaStatus.CREATED;

  public void run(ValidationProperties validationProperties) {
    if (partyMembers.size() < validationProperties.getMinPartyMembers()) {
      throw new UnsupportedOperationException("Secret Santa contains too few party members");
    }
    if (!SecretSantaStatus.CREATED.equals(status)) {
      throw new UnsupportedOperationException(String.format("Cannot run Secret Santa during %s status", status));
    }

    Collections.shuffle(partyMembers);
    for (int i = 0; i < partyMembers.size(); i++) {
      partyMembers.get(i).setRecipient(partyMembers.get((i + 1) % partyMembers.size()));
    }
    setStatus(SecretSantaStatus.COMPLETE);
  }

}
