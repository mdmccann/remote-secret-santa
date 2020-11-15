package com.northpole.remotesecretsanta.repoistory;

import com.northpole.remotesecretsanta.domain.PartyMember;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PartyMemberRepository extends CrudRepository<PartyMember, UUID> {
}
