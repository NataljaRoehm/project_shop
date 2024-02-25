package de.aittr.g_27_shop_project.domain.jpa;

import de.aittr.g_27_shop_project.domain.interfaces.Cart;
import de.aittr.g_27_shop_project.domain.interfaces.Product;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "cart")
public class JpaCart implements Cart {
  private static  Logger logger = LoggerFactory.getLogger(JpaCart.class);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @OneToOne
  @JoinColumn(name = "customer_id")
  private JpaCustomer customer;

  @ManyToMany
  @JoinTable(
      name = "cart_product",
      joinColumns = @JoinColumn(name = "cart_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id")
  )
  private List<JpaProduct> products = new ArrayList<>();

  public JpaCart(int id, JpaCustomer customer, List<JpaProduct> products) {
    this.id = id;
    this.customer = customer;
    this.products = products;
    logger.info(String.format("Создан новый экземпляр JpaCart с id: {%d},"
        + " customer: {}, products: {}", id, customer, products));
  }

  public JpaCart() {
    logger.info("Создана пустая корзина ");
  }

  public JpaCart(int id, List<JpaProduct> products) {
    this.id = id;
    this.products = products;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }


  public JpaCustomer getCustomer() {
    return customer;
  }

  public void setCustomer(JpaCustomer customer) {
    this.customer = customer;
  }

  @Override
  public List<Product> getProducts() {
    return new ArrayList<>(products
        .stream()
        .filter(x-> x.isActive())
        .toList());
  }

  @Override
  public void setProducts(List<Product> products) {
    this.products = products.stream().map(x -> {
      JpaProduct entity = new JpaProduct();
      entity.setId(x.getId());
      entity.setName(x.getName());
      entity.setActive(x.isActive());
      entity.setPrice(x.getPrice());
      return entity;
    }).toList();
    logger.info(String.format("Список всех продуктов: {%d}", this.products));
  }

  @Override
  public void addProduct(Product product) {
    products.add((JpaProduct)product);
    logger.info("Добавлен продукт: {}", product);
  }

  @Override
  public void deleteProductById(int productId) {
    products.removeIf(x->x.getId()==productId);
    logger.info("Удалённый продукт с ID: {}", productId);
  }

  @Override
  public void clear() {
    products.clear();
    logger.info("Очистка корзины");
  }

  @Override
  public double getTotalPrice() {
    double totalPrice = products.stream().filter(x-> x.isActive())
        .mapToDouble(product -> product.getPrice())
        .sum();
    logger.info("Общая стоимость продуктов: {}", totalPrice);
    return totalPrice;
  }

  @Override
  public double getAveragePrice() {
    if (products.isEmpty()) {
      logger.warn("Невозможно рассчитать среднюю стоимость для пустой корзины.");
      return 0.0;
    }
    double averagePrice = products.stream().filter(x->x.isActive())
        .mapToDouble(product -> product.getPrice())
        .average()
        .orElse(0.0); // Или другое значение по умолчанию

    logger.info("Средняя стоимость продуктов: {}", averagePrice);
    return averagePrice;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    JpaCart jpaCart = (JpaCart) o;
    return id == jpaCart.id && Objects.equals(customer, jpaCart.customer)
        && Objects.equals(products, jpaCart.products);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, customer, products);
  }

  @Override
  public String toString() {
    return "JpaCart{" +
        "id=" + id +
        ", customer=" + customer +
        ", products=" + products +
        '}';
  }
}