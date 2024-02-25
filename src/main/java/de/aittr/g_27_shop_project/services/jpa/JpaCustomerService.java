package de.aittr.g_27_shop_project.services.jpa;

import de.aittr.g_27_shop_project.domain.dto.CustomerDto;
import de.aittr.g_27_shop_project.domain.interfaces.Customer;
import de.aittr.g_27_shop_project.domain.jpa.JpaCart;
import de.aittr.g_27_shop_project.domain.jpa.JpaCustomer;
import de.aittr.g_27_shop_project.repositories.jpa.JpaCustomerRepository;
import de.aittr.g_27_shop_project.services.interfaces.CustomerService;
import de.aittr.g_27_shop_project.services.mapping.CustomerMappingService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

  @Service
  public class JpaCustomerService implements CustomerService {

    private JpaCustomerRepository repository;
    private CustomerMappingService mappingService;
    private JpaProductService productService;

    public JpaCustomerService(JpaCustomerRepository repository, CustomerMappingService mappingService, JpaProductService productService) {
      this.repository = repository;
      this.mappingService = mappingService;
      this.productService = productService;
    }


    @Override
    public CustomerDto save(CustomerDto dto) {
      JpaCustomer entity = mappingService.mapDtoToJpaCustomer(dto);
      JpaCart cart = new JpaCart();
      entity.setCart(cart);
      cart.setCustomer(entity);

      entity = repository.save(entity);

      return mappingService.mapCustomerEntityToDto(entity);
    }

    @Override
    public List<CustomerDto> getAllActiveCustomers() {
      return repository.findAll()
          .stream()
          .filter(c -> c.isActive())
          .map(c -> mappingService.mapCustomerEntityToDto(c))
          .toList();
    }

    @Override
    public CustomerDto getActiveCustomerById(int id) {
      JpaCustomer entity = repository.findById(id).orElse(null);

      if (entity != null && entity.isActive()) {
        return mappingService.mapCustomerEntityToDto(entity);
      }

      return null;
    }

    @Override
    public void update(CustomerDto customer) {

    }


    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public void restoreById(int id) {

    }

    @Override
    public int getActiveCustomersCount() {
      return 0;
    }

    @Override
    public double getTotalCartPriceById(int customerId) {
      return 0;
    }

    @Override
    public double getAveragePriceById(int customerId) {
      return 0;
    }

    @Override
    @Transactional
    public void addProductToCart(int customerId, int productId) {

    }

    @Override
    public void deleteProductFromCart(int customerId, int productId) {

    }

    @Override
    public void clearCartById(int customerId) {

    }
  }