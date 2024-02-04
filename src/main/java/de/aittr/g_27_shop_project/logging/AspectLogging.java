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
//@Pointcut("execution(* de.aittr.g_27_shop_project.services.jpa.JpaProductService.*(..))")
//public void productServiceMethods() {
//}

//@Before("productServiceMethods()")
//public void logMethodCall(JoinPoint joinPoint) {
//  String methodName = joinPoint.getSignature().getName();
//  Object[] args = joinPoint.getArgs();
//  logger.info("Вызван метод " + methodName + " с параметром: " + Arrays.toString(args));
//}

//@AfterReturning(pointcut = "productServiceMethods()", returning = "result")
//public void logMethodReturn(JoinPoint joinPoint, Object result) {
//  String methodName = joinPoint.getSignature().getName();
//  logger.info("Method " + methodName + " вернул: " + result);
//}

//@AfterThrowing(pointcut = "productServiceMethods()", throwing = "exception")
//public void logMethodException(JoinPoint joinPoint, Throwable exception) {
//  String methodName = joinPoint.getSignature().getName();
//  logger.error("Метод " + methodName + " вызвал исключение: " + exception.getMessage());
//}

 // В
  @Pointcut("execution(* src.main.java.de.aittr.g_27_shop_project.services..*(..))")
  public void serviceMethods() {}

  @Before("serviceMethods()")
  public void logMethodCall2(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    String className = joinPoint.getTarget().getClass().getSimpleName();
    Object[] args = joinPoint.getArgs();
    logger.info("Метод " + methodName + " класса " + className
        + " вызван с аргументами: " + Arrays.toString(args));
  }

  @AfterReturning(pointcut = "serviceMethods()", returning = "result")
  public void logMethodReturn2(JoinPoint joinPoint, Object result) {
    String methodName = joinPoint.getSignature().getName();
    String className = joinPoint.getTarget().getClass().getSimpleName();
    logger.info("Метод " + methodName + " класса " + className
        + " успешно вернул результат: " + result);
  }

  @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
  public void logMethodException2(JoinPoint joinPoint, Throwable exception) {
    String methodName = joinPoint.getSignature().getName();
    String className = joinPoint.getTarget().getClass().getSimpleName();
    logger.error(String.format("Метод " + methodName + " класса " + className
        + " выбросил исключение %s: " + exception.getMessage()));
  }

  @After("serviceMethods()")
  public void logMethodCompletion(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    String className = joinPoint.getTarget().getClass().getSimpleName();
    logger.info("Метод " + methodName + " класса " + className + " завершил работу");
  }
}
