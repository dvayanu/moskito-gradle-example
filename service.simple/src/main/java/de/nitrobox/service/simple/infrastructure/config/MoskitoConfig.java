package de.nitrobox.service.simple.infrastructure.config;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.anotheria.moskito.web.filters.AsyncSourceTldFilter;
import net.anotheria.moskito.web.filters.DebugRequestFilter;
import net.anotheria.moskito.web.filters.GenericMonitoringFilter;
import net.anotheria.moskito.web.filters.JSTalkBackFilter;
import net.anotheria.moskito.web.filters.JourneyFilter;
import net.anotheria.moskito.web.filters.JourneyStarterFilter;
import net.anotheria.moskito.web.filters.MoskitoCommandFilter;
import net.anotheria.moskito.web.filters.SourceIpSegmentFilter;
import net.anotheria.moskito.web.filters.SourceTldFilter;
import net.anotheria.moskito.web.session.SessionByTldListener;
import net.anotheria.moskito.web.session.SessionCountProducer;
import net.anotheria.moskito.webui.embedded.MoSKitoInspectStartException;
import net.anotheria.moskito.webui.embedded.StartMoSKitoInspectBackendForRemote;
import net.anotheria.moskito.webui.util.SetupPreconfiguredAccumulators;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MoskitoConfig {

  @PostConstruct
  private void init() {
    startMoskitoBackend();
    initPreconfiguredAccumulators();
  }

  private void startMoskitoBackend() {
    try {
      log.info("Starting Moskito backend for remote");
      StartMoSKitoInspectBackendForRemote.startMoSKitoInspectBackend(49194);
    } catch (MoSKitoInspectStartException e) {
      log.error("Error while starting Moskito inspect backend", e);
    }
  }

  private void initPreconfiguredAccumulators() {
    log.info("Setup preconfigured Moskito accumulators");
    SetupPreconfiguredAccumulators.setupUrlAccumulators();
    SetupPreconfiguredAccumulators.setupSessionCountAccumulators();
  }

  @Bean
  public FilterRegistrationBean journeyStarterFilter() {
    log.info("Registering JourneyStarterFilter");
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new JourneyStarterFilter());
//    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean asyncSourceTldFilter() {
    log.info("Registering AsyncSourceTldFilter");
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new AsyncSourceTldFilter());
//    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean debugRequestFilter() {
    log.info("Registering DebugRequestFilter");
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new DebugRequestFilter());
//    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean genericMonitoringFilter() {
    log.info("Registering GenericMonitoringFilter");
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new GenericMonitoringFilter());
//    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean journeyFilter() {
    log.info("Registering JourneyFilter");
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new JourneyFilter());
//    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }

//  @Bean
//  public FilterRegistrationBean jSTalkBackFilter() {
//    log.info("Registering JSTalkBackFilter");
//    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//    registrationBean.setFilter(new JSTalkBackFilter());
//    registrationBean.addUrlPatterns("/*");
//    return registrationBean;
//  }

  @Bean
  public FilterRegistrationBean moskitoCommandFilter() {
    log.info("Registering MoskitoCommandFilter");
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new MoskitoCommandFilter());
//    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean sourceIpSegmentFilter() {
    log.info("Registering SourceIpSegmentFilter");
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new SourceIpSegmentFilter());
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean sourceTldFilter() {
    log.info("Registering SourceTldFilter");
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new SourceTldFilter());
//    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }

  @Bean
  public SessionByTldListener sessionByTldListener() {
    log.info("Registering SessionByTldListener");
    return new SessionByTldListener();
  }

  @Bean
  public SessionCountProducer sessionCountProducer() {
    log.info("Registering SessionCountProducer");
    return new SessionCountProducer();
  }
}
