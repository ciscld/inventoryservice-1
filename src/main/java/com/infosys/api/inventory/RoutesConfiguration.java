package com.infosys.api.inventory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@Configuration
public class RoutesConfiguration {

    @Bean
    RouterFunction<?> routerFunction(InventoryService inventoryService) {
        return RouterFunctions.route(RequestPredicates.GET("/inventory"),
                req -> ServerResponse.ok().body(inventoryService.getAllInventory(), Inventory.class)
        ).andRoute(RequestPredicates.GET("/inventory/{id}"),
                req -> ServerResponse.ok()
                        .body(inventoryService.findInventoryByName(req.pathVariable("id")), Inventory.class)
        ).andRoute(RequestPredicates.GET("/inventory/{id}/events"),
                req -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(inventoryService.getEvents(req.pathVariable("id")), InventoryEvent.class)
        ).andRoute(RequestPredicates.POST("inventory/{id}/reserve").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
                req -> {
                    System.out.println("Reserving inventory for the given identifier");
                  return   ServerResponse.accepted().build();
                });
    }
}
