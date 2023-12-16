package project.reflection.annotations;

import project.reflection.consts.DataType;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
  String columnName();

  DataType type();
}
