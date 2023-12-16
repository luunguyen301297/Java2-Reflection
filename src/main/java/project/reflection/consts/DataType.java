package project.reflection.consts;

public enum DataType {
  VARCHAR("VARCHAR"),
  BIGINT("BIGINT"),
  FLOAT("FLOAT");
  public final String val;

  DataType(String val) {
    this.val = val;
  }
}
