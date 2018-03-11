package es.dersuzzala.futmonguer;

import es.dersuzzala.futmonguer.service.TeamCalculatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;


@EnableAutoConfiguration
@ComponentScan("es.dersuzzala.futmonguer")
@SpringBootApplication
public class Application {


    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        context.getBean(TeamCalculatorService.class).calculateTeams();
    }
}