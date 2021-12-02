package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.repository;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    boolean existsByDiscordId(@NotNull long discordId);
    boolean existsByEmail(@Email @NotBlank String email);
}
