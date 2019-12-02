package de.nitrobox.service.simple.service;

import org.springframework.stereotype.Service;
import net.anotheria.moskito.aop.annotation.Monitor;

@Service
@Monitor
public class SimpleService {

  public void someMethod(String param) {
    System.out.println("someMethod was called: " + param);
  }

}
