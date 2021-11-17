package com.github.marcindabrowski.example.nbpcurrencyexchange;

import com.github.marcindabrowski.example.nbpcurrencyexchange.config.AccountsRepositoryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableConfigurationProperties(AccountsRepositoryConfig.class)
@EnableFeignClients
@SpringBootApplication
public class NbpExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NbpExchangeApplication.class, args);
    }

}
