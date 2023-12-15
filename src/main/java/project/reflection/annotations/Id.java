package project.reflection.annotations;

import project.reflection.consts.DataType;

import java.lang.annotation.*;

/**
 * @author : ad
 * @mailto : luunguyen301297@gmail.com
 * @created : 11/13/2023, Monday
 **/
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
  String columName();

  DataType type();

  boolean autoIncrement() default true;
}
