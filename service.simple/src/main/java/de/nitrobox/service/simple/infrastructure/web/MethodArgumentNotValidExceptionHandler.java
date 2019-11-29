package de.nitrobox.service.simple.infrastructure.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSourceAccessor messageSourceAccessor;

  @Bean
  public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
    return new MessageSourceAccessor(messageSource);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    log.warn(ex.getMessage());
    ex.getBindingResult().getAllErrors()
        .forEach(error -> log.warn("Validation error {}", error));

    final Stream<Object> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
        .map(err -> new FieldErrorInfo(err.getField(), messageSourceAccessor.getMessage(err),
            err.getRejectedValue()));
    final Stream<Object> globalErrors = ex.getBindingResult().getGlobalErrors().stream().map(err ->
        new GlobalErrorInfo(err.getObjectName(), messageSourceAccessor.getMessage(err)));

    final List<Object> errorsList = Stream.concat(fieldErrors, globalErrors)
        .collect(Collectors.toList());

    final ApiError apiError = new ApiError(status, "Validation failed", errorsList);
    return new ResponseEntity<>(apiError, headers, status);
  }

  @Value
  private static class FieldErrorInfo {

    String fieldName;
    String error;
    Object rejectedValue;
  }

  @Value
  private static class GlobalErrorInfo {

    Object objectName;
    String error;
  }

  @Data
  private static class ApiError {

    private HttpStatus status;
    private String message;
    private List<Object> errors;

    ApiError(HttpStatus status, String message, List<Object> errors) {
      super();
      this.status = status;
      this.message = message;
      this.errors = errors;
    }


  }
}
