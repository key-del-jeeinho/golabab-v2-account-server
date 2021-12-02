package io.github.key_del_jeeinho.golabab_v2.account_server.account.entity

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.entity.AccountEntity
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.AccountDto
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.Role
import spock.lang.Specification

class AccountEntityTest extends Specification {

    /*  Entity 및 DTO 간 Convert 테스트
        - Entity 가 DTO 로 변환 가능한지 테스트한다.
        - DTO 가 Entity 로 변환 가능한지 테스트한다.
        - Entity, DTO 간 상호 변환시, 데이터의 왜곡이 없는지 테스트한다.
     */
    def "DTO 에서 Entity 로의 변환이 제대로 일어나는지 테스트"() {
        given:
        AccountDto dto = new AccountDto(계정id, 이메일, 역할, 디스코드id);

        when:
        AccountEntity entity = AccountEntity.of(dto);

        then:
        entity.getId() == 계정id
        entity.getEmail() == 이메일
        entity.getRole() == 역할
        entity.getDiscordId() == 디스코드id

        where:
        계정id | 이메일 | 역할 | 디스코드id
        1431L | "s20072@gsm.hs.kr" | Role.ADMIN | 3921L
        9243L | "s20082@gsm.hs.kr" | Role.DEVELOPER | 45292523L
        98436L | "s21732@gsm.hs.kr" | Role.USER | 123921L
    }

    def "Entity 에서 DTO 로의 변환이 제대로 일어나는지 테스트"() {
        given:
        AccountEntity entity = AccountEntity.builder()
                .id(계정id)
                .email(이메일)
                .role(역할)
                .discordId(디스코드id)
                .build();

        when:
        AccountDto dto = entity.toDto()

        then:
        dto.id() == 계정id
        dto.email() == 이메일
        dto.role() == 역할
        dto.discordId() == 디스코드id

        where:
        계정id | 이메일 | 역할 | 디스코드id
        1431L | "s20072@gsm.hs.kr" | Role.ADMIN | 3921L
        9243L | "s20082@gsm.hs.kr" | Role.DEVELOPER | 45292523L
        98436L | "s21732@gsm.hs.kr" | Role.USER | 123921L
    }
}