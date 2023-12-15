package project.reflection.consts;

/**
 * @author : ad
 * @mailto : luunguyen301297@gmail.com
 * @created : 11/13/2023, Monday
 **/
public enum DataType {
  VARCHAR("VARCHAR"),
  INT("INT"),
  BIGINT("BIGINT"),
  FLOAT("FLOAT");
  public final String val;

  private DataType(String val) {
    this.val = val;
  }
}
