package de.aittr.g_27_shop_project.domain.interfaces;

import de.aittr.g_27_shop_project.domain.jpa.JpaProduct;

public interface Product {

  int getId();

  boolean isActive();

  String getName();

  double getPrice();

  void setId(int id);

  void setActive(boolean isActive);

  void setName(String name);

  void setPrice(double price);

}