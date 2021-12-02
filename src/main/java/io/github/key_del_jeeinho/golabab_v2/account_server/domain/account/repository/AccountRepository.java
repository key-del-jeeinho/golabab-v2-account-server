package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.repository;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
