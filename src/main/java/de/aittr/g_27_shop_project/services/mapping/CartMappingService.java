package de.aittr.g_27_shop_project.services.mapping;

import de.aittr.g_27_shop_project.domain.dto.CartDto;
import de.aittr.g_27_shop_project.domain.dto.ProductDto;
import de.aittr.g_27_shop_project.domain.interfaces.Cart;
import de.aittr.g_27_shop_project.domain.interfaces.Product;
import de.aittr.g_27_shop_project.domain.jdbc.CommonCart;
import de.aittr.g_27_shop_project.domain.jdbc.CommonProduct;
import de.aittr.g_27_shop_project.domain.jpa.JpaCart;
import de.aittr.g_27_shop_project.domain.jpa.JpaProduct;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartMappingService {

  private ProductMappingService productMappingService;

  public CartDto mapCartEntityToDto(Cart cart) {
    int id = cart.getId();
    List<ProductDto> productDto = cart.getProducts().stream()
        .map(x-> productMappingService.mapEntityToDto(x)).toList();

    return new CartDto(id,productDto);

  }

  public JpaCart mapDtoToJpaCart(CartDto cartDto){
    int id = cartDto.getId();
    List<JpaProduct> products = cartDto.getProducts().stream()
        .map(x->productMappingService.mapDtoToEntity(x)).toList();
    return new JpaCart(id,products);
  }

  public CommonCart mapDtoToCommonCart(CartDto cartDto){
    int id = cartDto.getId();
    List<Product> products = cartDto.getProducts().stream()
        .map(x-> new CommonProduct(x.getId(), x.getName(), x.getPrice()))
        .collect(Collectors.toList());
    return new CommonCart(id,products);
  }
}
