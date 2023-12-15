package project.reflection.annotations;

import java.lang.annotation.*;

/**
 * @author : ad
 * @mailto : luunguyen301297@gmail.com
 * @created : 12/11/2023, Monday
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
  public String value();
}