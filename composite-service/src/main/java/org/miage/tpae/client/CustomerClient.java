package org.miage.tpae.client;

import org.miage.tpae.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @GetMapping("/api/clients/{id}")
    ClientDTO getClient(@PathVariable("id") long id);
}
