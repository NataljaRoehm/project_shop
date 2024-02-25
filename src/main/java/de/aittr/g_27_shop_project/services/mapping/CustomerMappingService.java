package de.aittr.g_27_shop_project.services.mapping;

import de.aittr.g_27_shop_project.domain.dto.CustomerDto;
import de.aittr.g_27_shop_project.domain.interfaces.Customer;
import de.aittr.g_27_shop_project.domain.jdbc.CommonCustomer;
import de.aittr.g_27_shop_project.domain.jpa.JpaCustomer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMappingService {
  private CartMappingService cartMappingService;

  public CustomerDto mapCustomerEntityToDto(Customer customer){
    int id = customer.getId();
    String name = customer.getName();
    int age = customer.getAge();
    String email = customer.getEmail();
//    CartDto cartDto = customer.getCart();
    return new CustomerDto(id,name,age,email);//cartDto);
    //this.id = id;
    //    this.name = name;
    //    this.age = age;
    //    this.email = email;
    //    this.cart = cart;
  }

  public JpaCustomer mapDtoToJpaCustomer(CustomerDto customerDto){
    String name = customerDto.getName();
    int age = customerDto.getAge();
    String email = customerDto.getEmail();
    return new JpaCustomer(name, age, email);
  }

  public CommonCustomer mapDtoToCommonCustomer(CustomerDto customerDto){
    int id = customerDto.getId();
    String name = customerDto.getName();
    int age = customerDto.getAge();
    String email = customerDto.getEmail();
    return new CommonCustomer(id,true,name,age,email);
    //private int id;
    //  private String name;
    //  private Cart cart;
    //  private int age;
    //  private String email;
  }
}
