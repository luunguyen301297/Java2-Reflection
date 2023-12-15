package project.reflection.consts;

/**
 * @author : ad
 * @mailto : luunguyen301297@gmail.com
 * @created : 11/13/2023, Monday
 **/
public enum StringSql {
  SELECT_CLAUSE("SELECT * FROM"),
  INSERT("INSERT INTO"),
  UPDATE("UPDATE"),
  DELETE("DELETE FROM"),
  SET("SET"),
  WHERE("WHERE"),
  VALUE("VALUE"),
  EQUAL("="),
  LIKE("LIKE"),
  GREATER_THAN(">"),
  GREATER_THAN_OR_EQUAL_TO(">="),
  LESS_THAN("<"),
  LESS_THAN_OR_EQUAL_TO("<="),
  NOT_EQUAL_TO("<>"),
  IN("IN"),
  OR("OR"),
  SPACE(" "),
  OPEN_PARENTHESIS("("),
  CLOSE_PARENTHESIS(")"),
  COMMA(","),
  QUESTION_MARK("?"),
  SEMI_COLON(";"),
  LIMIT("LIMIT"),
  OFFSET("OFFSET");
  public final String val;

  private StringSql(String val) {
    this.val = val;
  }
}
