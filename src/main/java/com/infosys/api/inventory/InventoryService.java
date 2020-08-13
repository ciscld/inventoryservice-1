package com.infosys.api.inventory;

import lombok.Setter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

  public   Flux<Inventory> getAllInventory() {
        return repository.findAll();
    }

  public   Mono<Inventory> findInventoryByName(String name) {
        return repository.findByName(name);
    }

    public Flux<InventoryEvent> getEvents(String inventoryId) {
      return   Flux.<InventoryEvent>generate(sink -> sink.next(new InventoryEvent(inventoryId, "", 1))
                ).delayElements(Duration.ofSeconds(1));

       /* return repository.findById(inventoryId)
                .flatMapMany(e ->
                        Flux.<InventoryEvent>generate(sink -> sink.next(new InventoryEvent(inventoryId, e.getName(), e.getQuantity())))
                ).delayElements(Duration.ofSeconds(1)); */
    }


}
