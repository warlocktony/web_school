package ru.hogwarts.school.controller;

import liquibase.pro.packaged.I;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/getPort")
    public Integer getPort(){
        return port;
    }
}
