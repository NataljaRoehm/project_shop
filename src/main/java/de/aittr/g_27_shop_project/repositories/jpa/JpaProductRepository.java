package de.aittr.g_27_shop_project.repositories.jpa;

import de.aittr.g_27_shop_project.domain.jpa.JpaProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaProductRepository extends JpaRepository<JpaProduct, Integer> {

  JpaProduct findByName(String name);
  int countAllByIsActiveIsTrue();

  @Query(value = "select * from product order by id desc limit 1;", nativeQuery = true)
  JpaProduct getLastProduct();
}