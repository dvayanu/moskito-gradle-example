package de.nitrobox.service.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PostConstruct;
import net.anotheria.moskito.webui.embedded.StartMoSKitoInspectBackendForRemote;
import net.anotheria.moskito.webui.embedded.MoSKitoInspectStartException;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    System.out.println("Hello!");
    SpringApplication.run(Application.class, args);
  }

  @PostConstruct
  public void startMoSKito() throws MoSKitoInspectStartException {
//    int moskitoPort = 49194;
//    System.out.println("Starting Moskito backend on " + moskitoPort + " port! !");
//    StartMoSKitoInspectBackendForRemote.startMoSKitoInspectBackend(moskitoPort);
//    System.out.println("Starting Moskito backend on " + moskitoPort
//        + " port! Performed successfully!");
  }
}