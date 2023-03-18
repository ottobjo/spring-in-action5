package com.cobsweden.learn.tacocloud.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.Optional;


@Slf4j
@Aspect
@Component
public class LoggerAop {

  @Before(value = "@annotation(com.cobsweden.learn.tacocloud.aop.LogAop)")
  public void logOperation(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String methodName = signature.getMethod().getName();

    Object[] args = joinPoint.getArgs();

    logModel(methodName, getObjOfClass(args, ModelObject.class).orElse(null));
    getObjOfClass(args, Errors.class).ifPresent(e -> logErrors(methodName, e));
  }

  private void logModel(String methodName, ModelObject modelObject) {
    log.info("[aop] {} Processing with model {}", methodName, modelObject);
  }

  private void logErrors(String methodName, Errors errors) {
    errors.getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .forEach(e -> log.info("[aop] {} Error {}", methodName, e));
  }

  private <T> Optional<T> getObjOfClass(Object[] args, Class<T> clazz) {
    return Arrays.stream(args)
        .filter(e -> isInstance(e, clazz))
        .map(e -> (T)e)
        .findFirst();
  }

  private boolean isInstance(Object object, Class<?> type) {
    return type.isInstance(object);
  }
}
