package de.nitrobox.service.simple.infrastructure.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class TenantHeaderRequestParamHandler implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    return methodParameter.getParameterAnnotation(TenantHeader.class) != null;
  }

  @Override
  public Object resolveArgument(
      MethodParameter methodParameter,
      ModelAndViewContainer modelAndViewContainer,
      NativeWebRequest nativeWebRequest,
      WebDataBinderFactory webDataBinderFactory) {
    String result = nativeWebRequest.getHeader("X-Nitrobox-Tenant-Id");
    if (result == null) {
      throw new IllegalArgumentException("X-Nitrobox-Tenant-Id header is required for path");
    }
    try {
      return Long.parseLong(result);
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("X-Nitrobox-Tenant-Id header is invalid: " + result, ex);
    }
  }
}