package project.reflection.test_product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.reflection.annotations.Column;
import project.reflection.annotations.Id;
import project.reflection.annotations.Table;
import project.reflection.consts.DataType;

/**
 * @author : ad
 * @mailto : luunguyen301297@gmail.com
 * @created : 12/14/2023, Thursday
 **/
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

//  private static void getSql(Class clazz) {
//    Table tableAnnotation = (Table) clazz.getAnnotation(Table.class);
//    String tableName = clazz.getSimpleName();
//    if (tableAnnotation != null) {
//      tableName = tableAnnotation.tableNAme();
//    } else {
//      System.out.println("Class not match entity class");
//    }
//    String idColumnName = "";
//    Field[] fields = clazz.getDeclaredFields();
//    for (Field field : fields) {
//      Id idAnnotation = field.getAnnotation(Id.class);
//      if (idAnnotation != null) {
//        idColumnName = idAnnotation.columName();
//        break;
//      }
//    }
//    if (StringUtils.isEmpty(idColumnName)) {
//      System.out.println("id column not exist");
//    }
//    String sql = new StringBuilder(StringSql.SELECT_CLAUSE.val).append(StringSql.SPACE.val)
//      .append(tableName).append(StringSql.SPACE.val)
//      .toString();
//    System.out.println(sql);
//  }
//
//  public static void main(String[] args) {
//    getSql(Product.class);
//  }
}
