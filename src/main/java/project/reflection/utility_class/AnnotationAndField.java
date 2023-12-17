package project.reflection.utility_class;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import project.reflection.annotations.Column;
import project.reflection.annotations.Id;
import project.reflection.annotations.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AnnotationAndField {
  private final Class<?> clazz;
  private String tableName;
  private String idColumnName;
  private final List<String> columnName;
  private final List<String> columnFieldName;
  private final List<String> allFieldName;

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
      throw new RuntimeException("Class not match entity class");
    }

    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      Id idAnnotation = field.getAnnotation(Id.class);
      if (idAnnotation != null) {
        this.idColumnName = idAnnotation.columName();
      }
      if (StringUtils.isEmpty(this.idColumnName)) {
        throw new RuntimeException("Id column is empty");
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
