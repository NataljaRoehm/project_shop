package de.aittr.g_27_shop_project.exception_handling.exceptions;

public class ProductNotActiveException extends RuntimeException{

  public ProductNotActiveException(String message) {
    super(message);
  }
}
