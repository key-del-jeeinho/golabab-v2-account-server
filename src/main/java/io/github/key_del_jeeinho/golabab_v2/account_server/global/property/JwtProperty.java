package io.github.key_del_jeeinho.golabab_v2.account_server.global.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter @Setter
public class JwtProperty {
    private String secret;
    private String issuer;
}
