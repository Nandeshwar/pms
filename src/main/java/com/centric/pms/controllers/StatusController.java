package com.centric.pms.controllers;

import com.centric.pms.models.Status;
import com.centric.pms.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
    @Autowired
    StatusService statusService;
    @GetMapping(value = "/v1/status", produces = "application/json")
    public Status status() {
        return statusService.appInfo();
    }
}
