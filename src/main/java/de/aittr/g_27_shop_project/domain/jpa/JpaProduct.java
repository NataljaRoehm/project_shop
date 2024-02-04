package de.aittr.g_27_shop_project.domain.jpa;

import de.aittr.g_27_shop_project.domain.interfaces.Product;
import jakarta.persistence.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "product")
public class JpaProduct implements Product {

  private static final Logger logger = LoggerFactory.getLogger(JpaProduct.class);
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  // Test - OK
  // TEST - X
  // Tes - X
  // test - X
  // TEst - X
  // Test@ - X
  // Test7 - X
  @Column(name = "name")
  //@NotNull
  //@NotBlank
  @Pattern(regexp = "[A-Z][a-x]{3,}")
  private String name;

  @Column(name = "price")
  @Max(90000)
  @Min(10)
  private double price;

  @Column(name = "is_active")
  private boolean isActive;

  public JpaProduct() {
    logger.info("Вызван конструктор JpaProduct без аргументов");
  }

  public JpaProduct(int id, String name, double price, boolean isActive) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.isActive = isActive;
    logger.info("Вызван конструктор JpaProduct с аргументами (id={},"
        + " name={}, price={}, isActive={})", id, name, price, isActive);
  }

  @Override
  public int getId() {
    logger.info("Метод getId вызван");
    return id;
  }

  @Override
  public void setId(int id) {
    logger.info("Метод setId вызван с аргументом: {}", id);
    this.id = id;
  }

  @Override
  public String getName() {
    logger.info("Метод getName вызван");
    return name;
  }

  @Override
  public void setName(String name) {
    logger.info("Метод setName вызван с аргументом: {}", name);
    this.name = name;
  }

  @Override
  public double getPrice() {
    logger.info("Метод getPrice вызван");
    return price;
  }

  @Override
  public void setPrice(double price) {
    logger.info("Метод setPrice вызван с аргументом: {}", price);
    this.price = price;
  }

  @Override
  public boolean isActive() {
    logger.info("Метод isActive вызван");
    return isActive;
  }

  @Override
  public void setActive(boolean active) {
    logger.info("Метод setActive вызван с аргументом: {}", active);
    isActive = active;
  }

  @Override
  public boolean equals(Object o) {
    logger.info("Метод equals вызван");
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JpaProduct that = (JpaProduct) o;
    return id == that.id && Double.compare(that.price, price) == 0 && isActive == that.isActive
        && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    logger.info("Метод hashCode вызван");
    return Objects.hash(id, name, price, isActive);
  }

  @Override
  public String toString() {
    logger.info("Метод toString вызван");
    return "JpaProduct{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", price=" + price +
        ", isActive=" + isActive +
        '}';
  }
}