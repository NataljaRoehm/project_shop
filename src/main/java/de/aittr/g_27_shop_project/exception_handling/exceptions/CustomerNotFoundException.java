package de.aittr.g_27_shop_project.exception_handling.exceptions;

public class CustomerNotFoundException extends RuntimeException{

  public CustomerNotFoundException(String message) {
    super(message);
  }
}
