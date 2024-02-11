package de.aittr.g_27_shop_project.exception_handling.exceptions;

public class ProductIdNotFoundException extends RuntimeException{

  public ProductIdNotFoundException(String message) {
    super(message);
  }
}
