package de.aittr.g_27_shop_project.services.jpa;

import de.aittr.g_27_shop_project.domain.dto.ProductDto;
import de.aittr.g_27_shop_project.domain.jpa.JpaProduct;
import de.aittr.g_27_shop_project.domain.jpa.Task;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductIdNotFoundException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductNameNotFoundException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.FourthTestException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.IncorrectProductPriceException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductNotActiveException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ThirdTestException;
import de.aittr.g_27_shop_project.repositories.jpa.JpaProductRepository;
import de.aittr.g_27_shop_project.scheduling.ScheduleExecutor;
import de.aittr.g_27_shop_project.services.interfaces.ProductService;
import de.aittr.g_27_shop_project.services.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JpaProductService implements ProductService {

  private JpaProductRepository repository;
  private ProductMappingService mappingService;
  //  private Logger logger = LogManager.getLogger(JpaProductService.class);
  private Logger logger = LoggerFactory.getLogger(JpaProductService.class);

  public JpaProductService(JpaProductRepository repository, ProductMappingService mappingService) {
    this.repository = repository;
    this.mappingService = mappingService;
  }

  @Override
  public ProductDto save(ProductDto product) {
    Task task = new Task("Последний добавленный в БД продукт: " + product);
    ScheduleExecutor.scheduleTaskSave(task);
    try {
      JpaProduct entity = mappingService.mapDtoToEntity(product);
      entity.setId(0);
      entity = repository.save(entity);
      return mappingService.mapEntityToDto(entity);
    } catch (Exception e) {
      throw new FourthTestException(e.getMessage());
    }
  }

  @Override
  public List<ProductDto> getAllActiveProducts() {
    Task task = new Task("Method getAllActiveProducts called");
    //ScheduleExecutor.scheduleTaskExecution(task);
    ScheduleExecutor.lastFiveTask(task);
    return repository.findAll()
        .stream()
        .filter(x -> x.isActive())
        .map(x -> mappingService.mapEntityToDto(x))
        .toList();
  }

  @Override
  public ProductDto getActiveProductById(int id) {
    // logger.info( String.format("Запрошен продукт с ИД %d.", id));
    // logger.warn(String.format("Запрошен продукт с ИД %d.", id));
    // logger.error(String.format("Запрошен продукт с ИД %d.", id));
    JpaProduct product = repository.findById(id).orElse(null);

    if (product != null && product.isActive()) {
      return mappingService.mapEntityToDto(product);
    }
    throw new ThirdTestException("Продукт с указанным ИД отсутствует в БД");
  }

  @Override
  public void update(ProductDto product) {
    JpaProduct entity = mappingService.mapDtoToEntity(product);
    if (product.getPrice() != 0) {
      repository.save(entity);
    }
    throw new IncorrectProductPriceException("В вашем продукте отсутствует цена ");
  }

  @Override
  @Transactional
  public void deleteById(int id) {
    JpaProduct product = repository.findById(id).orElse(null);
    if (product != null && product.isActive()) {
      product.setActive(false);
    } else {
      throw new ProductIdNotFoundException("Такой продукт отсутствует в базе данных");
    }
  }

  @Override
  @Transactional
  public void deleteByName(String name) {
    JpaProduct product = repository.findByName(name);
    if (product != null && product.isActive()) {
      product.setActive(false);
    } else {
      throw new ProductNameNotFoundException("Продукта с таким наименованием нет в базе данных");
    }
  }

  @Override
  @Transactional
  public void restoreById(int id) {
    JpaProduct product = repository.findById(id).orElse(null);
    if (product != null) {
      product.setActive(true);
    } else {
      throw new ProductIdNotFoundException("Такой продукт отсутствует в базе данных");
    }
  }

  @Override
  public int getActiveProductsCount() {
    return repository.countAllByIsActiveIsTrue();
  }

  @Override
  public double getActiveProductsTotalPrice() {
    List<JpaProduct> allProducts = repository.findAll();
    return allProducts.stream()
        .filter(x -> x.isActive())
        .mapToDouble(x -> x.getPrice())
        .sum();
  }

  @Override
  public double getActiveProductsAveragePrice() {
    List<ProductDto> allProducts = getAllActiveProducts();
    if (getAllActiveProducts().isEmpty()) {
      throw new ProductNotActiveException("В наличии отсутствуют активные продукты");
    }
    return allProducts.stream()
        .mapToDouble(x -> x.getPrice())
        .average().getAsDouble();
  }
}