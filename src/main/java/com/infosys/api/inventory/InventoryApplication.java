package com.infosys.api.inventory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.util.BsonUtils;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @Bean
    ApplicationRunner demodata(InventoryRepository repository) {
        return args -> {
            repository.deleteAll().thenMany(
                    Flux.just("Istio and art of Proxy", "Docker", "Book of kubs",
                            "Microservice Design Pattern Book", "Reactive Spring guide", "Axon guide", "CQRS and event sourcing")
                            .map(name -> new Inventory(name, name.length()))
                            .flatMap(inventory -> repository.save(inventory)))
                    .thenMany(repository.findAll())
                    .subscribe(e -> System.out.println(e));
        };
    }
}
