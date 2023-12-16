package project.reflection.test_product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.reflection.annotations.Column;
import project.reflection.annotations.Id;
import project.reflection.annotations.Table;
import project.reflection.consts.DataType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(tableNAme = "product_table")
public class Product {
  @Id(columName = "id", type = DataType.BIGINT)
  private long id;
  @Column(columnName = "name", type = DataType.VARCHAR)
  private String name;
  @Column(columnName = "producer", type = DataType.VARCHAR)
  private String producer;
  @Column(columnName = "product_line", type = DataType.VARCHAR)
  private String productLine;
  @Column(columnName = "price", type = DataType.FLOAT)
  private float price;
}
