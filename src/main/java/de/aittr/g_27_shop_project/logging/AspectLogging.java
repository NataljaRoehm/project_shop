package de.aittr.g_27_shop_project.logging;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogging {

  private Logger logger = LoggerFactory.getLogger(AspectLogging.class);

  @Pointcut("execution(* de.aittr.g_27_shop_project.services."
      + "jpa.JpaProductService.getCountAllActiveProducts(..))")
  public void getProducts() {
  }

  @Before("getProducts()")
  public void beforeGetProduct(JoinPoint joinPoint) {
    logger.info("Вызван метод getCountAllActiveProducts");
  }

  @Pointcut("execution(* de.aittr.g_27_shop_project.services."
      + "jpa.JpaProductService.restoreById(..))")
  public void restore() {
  }

  @After("restore()")
  public void afterRestore(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    logger.info(String.format("Метод restoreById вызван с параметром %s", args[0]));
  }

  @Pointcut("execution(* de.aittr.g_27_shop_project.services."
      + "jpa.JpaProductService.getActiveProductById(..))")
  public void getProduct() {
  }

  @AfterReturning(
      pointcut = "getProduct()",
      returning = "result"
  )

  public void afterReturningProduct(Object result) {
    logger.info(String.format("Метод getActiveProductById успешно вернул объект %s", result));
  }

  @AfterThrowing(
      pointcut = "getProduct()",
      throwing = "e"
  )
  public void afterThrowing(Exception e) {
    logger.info(String.format("Метод getActiveProductById выбросил исключение %s", e.getMessage()));
  }
 // При помощи АОП сделать логирование всех методов сервиса продуктов (при помощи одного Pointcut).
  //Для задания Pointcut использовать JpaProductService.*(..).
 // В лог должно выводиться: А. Какой метод и с какими параметрами вызван. Б. Какой метод завершил работу.
// А

  @Pointcut("execution(* src.main.java.de.aittr.g_27_shop_project.services..*(..))")
  public void serviceMethods() {}

  @Before("serviceMethods()")
  public void beforeServiceMethods(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
    Object[] args = joinPoint.getArgs();
    logger.info("Метод " + methodName + " класса " + className
        + " вызван с аргументами: " + Arrays.toString(args));
  }
  @After("serviceMethods()")
  public void afterServiceMethods(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
    logger.info("Метод " + methodName + " класса " + className + " завершил работу");
  }

  @AfterReturning(pointcut = "serviceMethods()", returning = "returning")
    public void afterReturningServiceMethods(JoinPoint joinPoint, Object returning) {
      String methodName = joinPoint.getSignature().getName();
      String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
      logger.info(String.format("Метод " + methodName + " класса " + className
           + "с результатом: " + returning ));}


  @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
  public void afterThrowingServicesMethods(JoinPoint joinPoint, Throwable exception) {
    String methodName = joinPoint.getSignature().getName();
    String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
    logger.error(String.format("Метод " + methodName + " класса " + className
        + " выбросил исключение %s: " + exception.getMessage()));
  }
  }