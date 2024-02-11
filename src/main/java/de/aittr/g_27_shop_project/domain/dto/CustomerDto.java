package de.aittr.g_27_shop_project.domain.dto;

import java.util.Objects;

public class CustomerDto {

  private int id;
  private String name;
  private int age;
  private String email;
  private CartDto cart;

  public CustomerDto(int id, String name, int age, String email, CartDto cart) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.email = email;
    this.cart = cart;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public String getEmail() {
    return email;
  }

  public CartDto getCart() {
    return cart;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CustomerDto that)) {
      return false;
    }

    if (id != that.id) {
      return false;
    }
    if (age != that.age) {
      return false;
    }
    if (!Objects.equals(name, that.name)) {
      return false;
    }
    if (!Objects.equals(email, that.email)) {
      return false;
    }
    return Objects.equals(cart, that.cart);
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + age;
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (cart != null ? cart.hashCode() : 0);
    return result;
  }
  @Override
  public String toString() {
    return "CustomerDto{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", age=" + age +
        ", email='" + email + '\'' +
        ", cart=" + cart +
        '}';
  }
}