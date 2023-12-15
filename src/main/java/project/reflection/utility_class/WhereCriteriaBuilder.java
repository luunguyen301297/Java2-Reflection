package project.reflection.utility_class;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : ad
 * @mailto : luunguyen301297@gmail.com
 * @created : 12/13/2023, Wednesday
 **/
public class WhereCriteriaBuilder {
  private final List<String> criteriaList;

  public WhereCriteriaBuilder() {
    this.criteriaList = new ArrayList<>();
  }

  public WhereCriteriaBuilder equal(String column, String value) {
    criteriaList.add(column + " = " + value);
    return this;
  }

  public WhereCriteriaBuilder like(String column, String value) {
    criteriaList.add(column + " LIKE " + value);
    return this;
  }

  public WhereCriteriaBuilder greaterThan(String column, String value) {
    criteriaList.add(column + " > " + value);
    return this;
  }

  public WhereCriteriaBuilder lessThan(String column, String value) {
    criteriaList.add(column + " < " + value);
    return this;
  }

  public WhereCriteriaBuilder greaterThanOrEqualTo(String column, String value) {
    criteriaList.add(column + " >= " + value);
    return this;
  }

  public WhereCriteriaBuilder lessThanOrEqualTo(String column, String value) {
    criteriaList.add(column + " <= " + value);
    return this;
  }

  public WhereCriteriaBuilder notEqualTo(String column, String value) {
    criteriaList.add(column + " <> " + value);
    return this;
  }

  public WhereCriteriaBuilder in(String column, List<String> values) {
    criteriaList.add(column + " IN (" + String.join(",", values) + ")");
    return this;
  }

  public WhereCriteriaBuilder or(List<String> conditionList) {
    criteriaList.add(String.join(" OR ", conditionList));
    return this;
  }

  public WhereCriteriaBuilder between(String column, Object lowerBound, Object upperBound) {
    criteriaList.add(column + " BETWEEN " + lowerBound + " AND " + upperBound);
    return this;
  }

  public String build() {
    return " WHERE " + String.join(" AND ", criteriaList);
  }

  public static void main(String[] args) {
    WhereCriteriaBuilder builder = new WhereCriteriaBuilder();
    String condition = builder
      .equal("name", "'luu'")
      .greaterThan("id", "'10'")
      .in("address", Arrays.asList("'hanoi'", "'nghean'"))
      .build();

    WhereCriteriaBuilder builder2 = new WhereCriteriaBuilder();
    String condition2 = builder2
      .or(Arrays.asList("name = 'luu'", "address = 'nghean'"))
      .build();

    WhereCriteriaBuilder builder3 = new WhereCriteriaBuilder();
    String condition3 = builder3
      .between("id", 10, 20)
      .build();

    String sql = "select * from table" + condition;
    String sql2 = "select * select table" + condition2;
    String sql3 = "select * select table" + condition3;

    System.out.println(sql);
    System.out.println(sql2);
    System.out.println(sql3);
  }
}
