package io.github.key_del_jeeinho.golabab_v2.account_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class GolababV2AccountServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GolababV2AccountServerApplication.class, args);
    }

}
