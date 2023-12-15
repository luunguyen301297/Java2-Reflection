package project.reflection.utility_class;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import project.reflection.annotations.Column;
import project.reflection.annotations.Id;
import project.reflection.annotations.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : ad
 * @mailto : luunguyen301297@gmail.com
 * @created : 12/15/2023, Friday
 **/
@Data
@NoArgsConstructor
public class AnnotationAndField {
  private Class<?> clazz;
  private String tableName;
  private String idColumnName;
  private List<String> columnName;
  private List<String> columnFieldName;
  private List<String> allFieldName;

  public AnnotationAndField(Class<?> clazz) {
    this.clazz = clazz;
    columnName = new ArrayList<>();
    columnFieldName = new ArrayList<>();
    allFieldName = new ArrayList<>();

    Table tableAnnotation = clazz.getAnnotation(Table.class);
    tableName = clazz.getSimpleName();
    if (tableAnnotation != null) {
      tableName = tableAnnotation.tableNAme();
    } else {
      System.err.println("Class not match entity class");
    }

    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      Id idAnnotation = field.getAnnotation(Id.class);
      if (idAnnotation != null) {
        this.idColumnName = idAnnotation.columName();
      }
      if (StringUtils.isEmpty(this.idColumnName)) {
        System.err.println("Err");
      }

      Column columnAnnotation = field.getAnnotation(Column.class);
      if (columnAnnotation != null) {
        this.columnName.add(columnAnnotation.columnName());
        this.columnFieldName.add(field.getName());
      }
      this.allFieldName.add(field.getName());
    }
  }
}
