package com.lll.learn.controller;

import com.lll.learn.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/city")
public class CityWebFluxController {

    @GetMapping(value = "/{id}")
    public Mono<City> findCityById(@PathVariable("id") Long id) {
        City city = new City("上海", id);
        if (id == 1) {
            return Mono.create(cityMonoSink -> cityMonoSink.success(city));
        }
        if (id == 2) {
            return Mono.justOrEmpty(city);
        }
        return Mono.create(cityMonoSink -> cityMonoSink.success());
    }

    @GetMapping(value = "/handler/{id}")
    public Mono<City> handlerId(@PathVariable("id") Long id) {
        City city = new City("上海", id);
        if (id == 1) {
            return Mono.create(cityMonoSink -> cityMonoSink.success(city));
        }
        if (id == 2) {
            return Mono.justOrEmpty(city);
        }
        return Mono.create(cityMonoSink -> cityMonoSink.success());
    }

    @GetMapping()
    public Flux<City> findAllCity() {
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            City city = new City("绍兴" + i, (long) i);
            cities.add(city);
        }
        return Flux.fromIterable(cities);
    }

}