package de.aittr.g_27_shop_project.domain.jpa;

import de.aittr.g_27_shop_project.domain.interfaces.Cart;
import de.aittr.g_27_shop_project.domain.interfaces.Customer;
import jakarta.persistence.*;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "customer")
public class JpaCustomer implements Customer {
  private static final Logger logger = LoggerFactory.getLogger(JpaCustomer.class);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "is_active")
  private boolean isActive;

  @OneToOne(mappedBy = "customer")
  private JpaCart cart;

  public JpaCustomer() {
    logger.info("Вызван конструктор JpaCustomer без аргументов");
  }

  public JpaCustomer(String name, boolean isActive) {
    this.name = name;
    this.isActive = isActive;
    logger.info("Вызван конструктор JpaCustomer с аргументами (name={}, isActive={})", name, isActive);
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
    logger.info("Установлен id: {}", id);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
    logger.info("Установлено имя: {}", name);
  }

  @Override
  public boolean isActive() {
    logger.info("Метод isActive вызван");
    return isActive;
  }

  @Override
  public void setActive(boolean active) {
    isActive = active;
    logger.info("Установлен статус активности: {}", active);
  }

  @Override
  public Cart getCart() {
    return cart;
  }

  @Override
  public void setCart(Cart cart) {
    JpaCart entity = new JpaCart();
    entity.setId(cart.getId());
    entity.setProducts(cart.getProducts());
    this.cart = entity;
    logger.info("Установлен новый Cart для JpaCustomer: {}", entity);
  }

  @Override
  public boolean equals(Object o) {
    logger.info("Метод equals вызван");
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    JpaCustomer that = (JpaCustomer) o;
    return id == that.id && isActive == that.isActive &&
        Objects.equals(name, that.name) && Objects.equals(cart, that.cart);
  }

  @Override
  public int hashCode() {
    logger.info("Метод hashCode вызван");
    return Objects.hash(id, name, isActive, cart);
  }

  @Override
  public String toString() {
    logger.info("Метод toString вызван");
    return "JpaCustomer{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", isActive=" + isActive +
        ", cart=" + cart +
        '}';
  }
}