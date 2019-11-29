package de.nitrobox.service.simple.rest;

import de.nitrobox.service.simple.service.SimpleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import net.anotheria.moskito.aop.annotation.Monitor;

@Slf4j
@RestController
@RequestMapping("/api/simple")
@RequiredArgsConstructor
@Tag(name = "Simple")
@Monitor
public class SimpleResource {

  private final SimpleService simpleService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  String get() {
    simpleService.someMethod();
    return "Hello World!";
  }
}
