package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/support")
public class SupportController {

    @GetMapping
    public Integer sumNumber() {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(10_000_000)
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        return sum;
    }
}
