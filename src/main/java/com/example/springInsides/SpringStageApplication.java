package com.example.springInsides;

import com.example.springInsides.entity.Rocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringStageApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication. run(SpringStageApplication.class, args);
        run.getBean(Rocket.class).start();
    }

}
