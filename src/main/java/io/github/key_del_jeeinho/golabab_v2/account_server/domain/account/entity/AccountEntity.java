package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.entity;

import io.github.key_del_jeeinho.golabab_v2.rosetta.account.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter
@NoArgsConstructor
@Builder //Builder must on All Args Constructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Email @NotBlank
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @NotNull
    private long discordId;
}
