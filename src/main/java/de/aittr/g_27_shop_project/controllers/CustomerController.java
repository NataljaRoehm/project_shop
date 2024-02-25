package de.aittr.g_27_shop_project.controllers;

import de.aittr.g_27_shop_project.domain.dto.CustomerDto;
import de.aittr.g_27_shop_project.services.interfaces.CustomerService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  private CustomerService service;

  public CustomerController(CustomerService service) {
    this.service = service;
  }

  @PostMapping
  public CustomerDto save(@RequestBody CustomerDto customer) {
    return service.save(customer);
  }

  @GetMapping
  public List<CustomerDto> getAll() {
    return service.getAllActiveCustomers();
  }

  @GetMapping("/{id}")
  public CustomerDto getById(@PathVariable int id) {
    return service.getActiveCustomerById(id);
  }
}