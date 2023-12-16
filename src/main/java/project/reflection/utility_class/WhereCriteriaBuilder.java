package project.reflection.utility_class;

import project.reflection.consts.StringSql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WhereCriteriaBuilder {
  private final List<String> criteriaList;

  public WhereCriteriaBuilder() {
    this.criteriaList = new ArrayList<>();
  }

  public WhereCriteriaBuilder equal(String column, String value) {
    StringBuilder sb = new StringBuilder(column).append(StringSql.EQUAL.val).append(value);
    criteriaList.add(sb.toString());
    return this;
  }

  public WhereCriteriaBuilder like(String column, String value) {
    StringBuilder sb = new StringBuilder(column).append(StringSql.SPACE.val)
      .append(StringSql.LIKE.val).append(StringSql.SPACE.val)
      .append(value);
    criteriaList.add(sb.toString());
    return this;
  }

  public WhereCriteriaBuilder greaterThan(String column, String value) {
    StringBuilder sb = new StringBuilder(column).append(StringSql.GREATER_THAN.val).append(value);
    criteriaList.add(sb.toString());
    return this;
  }

  public WhereCriteriaBuilder lessThan(String column, String value) {
    StringBuilder sb = new StringBuilder(column).append(StringSql.LESS_THAN.val).append(value);
    criteriaList.add(sb.toString());
    return this;
  }

  public WhereCriteriaBuilder greaterThanOrEqualTo(String column, String value) {
    StringBuilder sb = new StringBuilder(column).append(StringSql.GREATER_THAN_OR_EQUAL_TO.val).append(value);
    criteriaList.add(sb.toString());
    return this;
  }

  public WhereCriteriaBuilder lessThanOrEqualTo(String column, String value) {
    StringBuilder sb = new StringBuilder(column).append(StringSql.LESS_THAN_OR_EQUAL_TO.val).append(value);
    criteriaList.add(sb.toString());
    return this;
  }

  public WhereCriteriaBuilder notEqualTo(String column, String value) {
    StringBuilder sb = new StringBuilder(column).append(StringSql.NOT_EQUAL_TO.val).append(value);
    criteriaList.add(sb.toString());
    return this;
  }

  public WhereCriteriaBuilder in(String column, List<String> values) {
    StringBuilder sb = new StringBuilder(column).append(StringSql.SPACE.val)
      .append(StringSql.IN.val).append(StringSql.OPEN_PARENTHESIS.val);
    for (int i = 0; i < values.size(); i++) {
      if (i > 0) {
        sb.append(StringSql.COMMA.val);
      }
      sb.append(values.get(i));
    }
    sb.append(StringSql.CLOSE_PARENTHESIS.val);
    criteriaList.add(sb.toString());
    return this;
  }

  public WhereCriteriaBuilder or(List<String> conditionList) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < conditionList.size(); i++) {
      if (i > 0) {
        sb.append(StringSql.SPACE.val).append(StringSql.OR.val).append(StringSql.SPACE.val);
      }
      sb.append(conditionList.get(i));
    }
    criteriaList.add(sb.toString());
    return this;
  }

  public WhereCriteriaBuilder between(String column, Object lower, Object upper) {
    StringBuilder sb = new StringBuilder(column).append(StringSql.SPACE.val)
      .append(StringSql.BETWEEN.val).append(StringSql.SPACE.val)
      .append(lower.toString()).append(StringSql.SPACE.val)
      .append(StringSql.AND.val).append(StringSql.SPACE.val)
      .append(upper.toString());
    criteriaList.add(sb.toString());
    return this;
  }

  public String build() {
    StringBuilder whereClause = new StringBuilder().append(StringSql.SPACE.val)
      .append(StringSql.WHERE.val).append(StringSql.SPACE.val);
    for (int i = 0; i < criteriaList.size(); i++) {
      if (i > 0) {
        whereClause.append(StringSql.SPACE.val)
          .append(StringSql.AND.val).append(StringSql.SPACE.val);
      }
      whereClause.append(criteriaList.get(i));
    }
    return whereClause.toString();
  }

  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    WhereCriteriaBuilder builder = new WhereCriteriaBuilder();
    String condition = builder
      .equal("name", "'luu'")
      .greaterThan("id", "10")
      .in("address", Arrays.asList("'hanoi'", "'nghean'"))
      .build();

    WhereCriteriaBuilder builder2 = new WhereCriteriaBuilder();
    String condition2 = builder2
      .or(Arrays.asList("name='luu'", "address='nghean'"))
      .build();

    String sql = "select * from table" + condition;
    String sql2 = "select * select table" + condition2;

    System.out.println(sql);
    System.out.println(sql2);
    System.out.println(System.currentTimeMillis() - startTime);
  }
}
