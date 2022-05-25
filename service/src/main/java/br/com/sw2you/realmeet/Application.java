package br.com.sw2you.realmeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("br.com.sw2you.realmeet.config.properties")//permitir que o spring faça o scaneamento das propriedades do pacote config
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
