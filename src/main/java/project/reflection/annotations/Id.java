package project.reflection.annotations;

import project.reflection.consts.DataType;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
  String columName();

  DataType type();

  boolean autoIncrement() default true;
}
