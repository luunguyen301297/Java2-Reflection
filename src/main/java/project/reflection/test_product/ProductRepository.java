package project.reflection.test_product;

import project.reflection.jpaRepo.impl.JpaRepositoryCloneImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends JpaRepositoryCloneImpl<Product> {
  public ProductRepository() {
    super(Product.class);
  }

  @Override
  protected List<Product> rowMapper(ResultSet resultSet) {
    List<Product> productList = new ArrayList<>();
    try {
      while (resultSet.next()) {
        productList.add(Product.builder()
          .id(resultSet.getLong("id"))
          .name(resultSet.getString("name"))
          .producer(resultSet.getString("producer"))
          .productLine(resultSet.getString("product_line"))
          .price(resultSet.getFloat("price"))
          .build());
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return productList;
  }
}
