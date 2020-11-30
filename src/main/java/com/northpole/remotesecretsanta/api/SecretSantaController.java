package com.northpole.remotesecretsanta.api;

import com.northpole.remotesecretsanta.config.ValidationProperties;
import com.northpole.remotesecretsanta.domain.SecretSanta;
import com.northpole.remotesecretsanta.domain.SecretSantaStatus;
import com.northpole.remotesecretsanta.service.SecretSantaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@BasePathAwareController
@RequiredArgsConstructor
@Transactional
public class SecretSantaController implements RepresentationModelProcessor<EntityModel<SecretSanta>> {

  private final SecretSantaService secretSantaService;
  private final ValidationProperties validationProperties;

  @PostMapping("/secretSantas/{id}/run")
  public ResponseEntity<?> run(@PathVariable UUID id) {
    return ResponseEntity.ok(secretSantaService.run(id));
  }

  @Override
  public EntityModel<SecretSanta> process(EntityModel<SecretSanta> model) {
    SecretSanta secretSanta = Objects.requireNonNull(model.getContent());
    List<Link> links = model.getLinks().toList();
    if (links.isEmpty()) {
      return model;
    }
    String selfHref = links.get(0).getHref();

    if (SecretSantaStatus.CREATED.equals(secretSanta.getStatus())
        && validationProperties.getMinPartyMembers() <= secretSanta.getPartyMembers().size()) {
      Link runLink = Link.of(selfHref + "/" + "run", "run");
      model.add(runLink);
    }

    return model;
  }
}
