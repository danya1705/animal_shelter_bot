package pro.sky.java.course7.animal_shelter_bot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class AnimalShelterBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalShelterBotApplication.class, args);
    }

}
