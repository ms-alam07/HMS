package com.hms.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hms/property")
public class PropertyController {

    @PostMapping("/addProperty")
    public String addProperty() {
        return "Property added successfully";
    }
}
