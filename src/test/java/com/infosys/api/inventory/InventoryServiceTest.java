package com.infosys.api.inventory;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class InventoryServiceTest {
    @Autowired
   private InventoryService inventoryService;

    @Test
    void getEventsTake10() throws Exception {
        String inventoryId = inventoryService.getAllInventory().blockFirst().getId();
        StepVerifier.withVirtualTime(() -> inventoryService.getEvents(inventoryId).take(10))
                .thenAwait(Duration.ofHours(10))
                .expectNextCount(10)
                .verifyComplete();
    }
}