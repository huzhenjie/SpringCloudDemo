package com.scrat.eureka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceInstanceRestController {

    private final DiscoveryClient discoveryClient;

    @Autowired
    public ServiceInstanceRestController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping(value = "/service-instances", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> serviceInstances() {
        return this.discoveryClient.getServices();
    }

    @GetMapping(value = "/service-instances/{applicationName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @GetMapping("/description")
    public String description() {
        return this.discoveryClient.description();
    }
}
