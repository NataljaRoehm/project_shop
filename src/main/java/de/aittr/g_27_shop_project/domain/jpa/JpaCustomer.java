package de.aittr.g_27_shop_project.domain.jpa;

import de.aittr.g_27_shop_project.domain.interfaces.Cart;
import de.aittr.g_27_shop_project.domain.interfaces.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "customer")
public class JpaCustomer implements Customer {
  private static Logger logger = LoggerFactory.getLogger(JpaCustomer.class);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  @Pattern(regexp = "[A-Z][a-z]{3,}")
  private String name;

  @Column(name = "age")
  @Min(16)
  @Max(80)
  private int age;

  @Column(name = "email")
  @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+"
      + "(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
  private String email;

  @Column(name = "is_active")
  private boolean isActive;

  @OneToOne(mappedBy = "customer")
  private JpaCart cart;

  public JpaCustomer() {
    logger.info("Вызван конструктор JpaCustomer без аргументов");
  }

  public JpaCustomer(String name, boolean isActive, int age, String email) {
    this.name = name;
    this.age = age;
    this.email = email;
    this.isActive = isActive;
    logger.info("Вызван конструктор JpaCustomer "
        + "с аргументами name=%s, age=%d, email=%s", name,age, email);
  }

  @Override
  public int getId() {
    logger.info("Вызван метод getId ");
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
    logger.info(String.format("Установлен id: %d", id));
  }

  @Override
  public String getName() {
    logger.info("Вызван метод getName ");
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

  public int getAge() {
    logger.info("Вызван метод getAge");
    return age;
  }

  public void setAge(int age) {
    logger.info("Метод getAge вызван с возрастом: age=%d");
    this.age = age;
  }

  public String getEmail() {
    logger.info("Метод getEmail вызван");
    return email;
  }

  public void setEmail(String email) {
    logger.info(String.format("Вызван метод setEmail с параметром email=%s", email));
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof JpaCustomer that)) {
      return false;
    }

    if (id != that.id) {
      return false;
    }
    if (age != that.age) {
      return false;
    }
    if (isActive != that.isActive) {
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
    result = 31 * result + (isActive ? 1 : 0);
    result = 31 * result + (cart != null ? cart.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    logger.info(String.format("Метод toString вызван. "
            + "С параметрами: id=%d, name=%s, isActive=%b, age=%d, email=%s",
        id, isActive, name, age));
    return "JpaCustomer{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", age=" + age +
        ", email='" + email + '\'' +
        ", isActive=" + isActive +
        ", cart=" + cart +
        '}';
  }
}