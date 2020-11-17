package com.northpole.remotesecretsanta.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Data
@Configuration
@ConfigurationProperties(prefix = "validation")
public class ValidationProperties {

  private BigDecimal minGiftCost;
  private Integer minPartyMembers;

}
