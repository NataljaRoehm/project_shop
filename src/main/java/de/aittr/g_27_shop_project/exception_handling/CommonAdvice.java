package de.aittr.g_27_shop_project.exception_handling;

import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductIdNotFoundException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductNameNotFoundException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.FourthTestException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.IncorrectProductPriceException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.PositiveException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductNotActiveException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ThirdTestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 3 способ - создание класса-адвайса
// Плюсы - данный адвайс является глобальным обработчиком,
// который ловит исключения, выброшенные в любом месте проекта.
// А также позволяет нам отправлять клиенту любой статус ответа
// и информативное сообщение об ошибке.
@ControllerAdvice
public class CommonAdvice {

  @ExceptionHandler(ThirdTestException.class)
  public ResponseEntity<Response> handleException(ThirdTestException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.I_AM_A_TEAPOT);
  }

  @ExceptionHandler(FourthTestException.class)
    public ResponseEntity<Response> handleException(FourthTestException e) {
      Response response = new Response(e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

  @ExceptionHandler(IncorrectProductPriceException.class)
  public ResponseEntity<Response> handleException(IncorrectProductPriceException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(ProductIdNotFoundException.class)
  public ResponseEntity<Response> handleException(ProductIdNotFoundException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ProductNameNotFoundException.class)
  public ResponseEntity<Response> handleException(ProductNameNotFoundException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PositiveException.class)
  public ResponseEntity<Response> handleException(PositiveException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @ExceptionHandler(ProductNotActiveException.class)
  public ResponseEntity<Response> handleException(ProductNotActiveException e) {
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }
}