package project.reflection.jpaRepo.impl;

import project.config.properties.Datasource;
import project.reflection.consts.StringSql;
import project.reflection.test_product.Product;
import project.reflection.test_product.ProductRepository;
import project.reflection.utility_class.AnnotationAndField;
import project.reflection.jpaRepo.JpaRepositoryClone;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

public abstract class JpaRepositoryCloneImpl<T> implements JpaRepositoryClone<T> {
  private final Class<?> clazz;
  private final String tableName;
  private final String idColumnName;
  private final List<String> columnName;
  private final List<String> columnFieldName;

  public JpaRepositoryCloneImpl(Class<?> clazz) {
    AnnotationAndField annotationAndField = new AnnotationAndField(clazz);
    this.clazz = clazz;
    this.tableName = annotationAndField.getTableName();
    this.idColumnName = annotationAndField.getIdColumnName();
    this.columnName = annotationAndField.getColumnName();
    this.columnFieldName = annotationAndField.getColumnFieldName();
  }

  protected abstract List<T> rowMapper(ResultSet resultSet);

  @Override
  public T getById(String id) {
    String sql = new StringBuilder(StringSql.SELECT_CLAUSE.val).append(StringSql.SPACE.val)
      .append(tableName).append(StringSql.SPACE.val)
      .append(StringSql.WHERE.val).append(StringSql.SPACE.val)
      .append(idColumnName).append(StringSql.EQUAL.val)
      .append(StringSql.QUESTION_MARK.val).append(StringSql.SEMI_COLON.val)
      .toString();
    System.out.println(sql);

    PreparedStatement preSt;
    Connection conn = Datasource.getConn();
    try {
      preSt = conn.prepareStatement(sql);
      preSt.setString(1, id);
      ResultSet resultSet = preSt.executeQuery();

      List<T> data = rowMapper(resultSet);
      if (!data.isEmpty()) {
        return data.get(0);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public List<T> getAll() {
    String sql = new StringBuilder(StringSql.SELECT_CLAUSE.val).append(StringSql.SPACE.val)
      .append(tableName).append(StringSql.SEMI_COLON.val)
      .toString();

    PreparedStatement preSt;
    Connection conn = Datasource.getConn();
    try {
      preSt = conn.prepareStatement(sql);
      ResultSet resultSet = preSt.executeQuery();

      List<T> data = rowMapper(resultSet);
      if (!data.isEmpty()) {
        return data;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public List<T> getAll(int limit, int offset) {
    String sql = new StringBuilder(StringSql.SELECT_CLAUSE.val).append(StringSql.SPACE.val)
      .append(tableName).append(StringSql.SPACE.val)
      .append(StringSql.LIMIT.val).append(StringSql.SPACE.val)
      .append(StringSql.QUESTION_MARK.val).append(StringSql.SPACE.val)
      .append(StringSql.OFFSET.val).append(StringSql.SPACE.val)
      .append(StringSql.QUESTION_MARK.val).append(StringSql.SEMI_COLON.val)
      .toString();

    PreparedStatement preSt;
    Connection conn = Datasource.getConn();
    try {
      preSt = conn.prepareStatement(sql);
      preSt.setInt(1, limit);
      preSt.setInt(2, offset);
      ResultSet resultSet = preSt.executeQuery();

      List<T> data = rowMapper(resultSet);
      if (!data.isEmpty()) {
        return data;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public boolean insert(T t) {
    String sql = new StringBuilder(StringSql.INSERT.val).append(StringSql.SPACE.val)
      .append(tableName).append(StringSql.SPACE.val)
      .append(getSQLFieldList()).append(StringSql.SPACE.val)
      .append(StringSql.VALUE.val).append(StringSql.SPACE.val)
      .append(getSQLParams()).append(StringSql.SEMI_COLON.val)
      .toString();
    System.out.println(sql);

    PreparedStatement preSt;
    Connection conn = Datasource.getConn();

    try {
      preSt = conn.prepareStatement(sql);
      setSQLFieldValue(t, preSt);
      preSt.execute();
      return true;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean insertBatch(List<T> tList) {
    String sql = new StringBuilder(StringSql.INSERT.val).append(StringSql.SPACE.val)
      .append(tableName).append(StringSql.SPACE.val)
      .append(getSQLFieldList()).append(StringSql.SPACE.val)
      .append(StringSql.VALUE.val).append(StringSql.SPACE.val)
      .append(getSQLParams()).append(StringSql.SEMI_COLON.val)
      .toString();
    System.out.println(sql);

    PreparedStatement preSt;
    Connection conn = Datasource.getConn();

    for (T t : tList) {
      try {
        preSt = conn.prepareStatement(sql);
        setSQLFieldValue(t, preSt);
        preSt.execute();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
    return true;
  }

  @Override
  public boolean deleteById(String id) {
    String sql = new StringBuilder(StringSql.DELETE.val).append(StringSql.SPACE.val)
      .append(tableName).append(StringSql.SPACE.val)
      .append(StringSql.WHERE.val).append(StringSql.SPACE.val)
      .append(idColumnName).append(StringSql.EQUAL.val)
      .append(StringSql.QUESTION_MARK.val).append(StringSql.SEMI_COLON.val)
      .toString();
    System.out.println(sql);

    PreparedStatement preSt;
    Connection conn = Datasource.getConn();
    try {
      preSt = conn.prepareStatement(sql);
      preSt.setString(1, id);
      preSt.executeUpdate();
      return true;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean updateById(String id, T t) {
    String sql = new StringBuilder(StringSql.UPDATE.val).append(StringSql.SPACE.val)
      .append(tableName).append(StringSql.SPACE.val)
      .append(StringSql.SET.val).append(StringSql.SPACE.val)
      .append(getSQLUpdateColumValue())
      .append(StringSql.WHERE.val).append(StringSql.SPACE.val)
      .append(idColumnName).append(StringSql.EQUAL.val)
      .append(StringSql.QUESTION_MARK.val).append(StringSql.SEMI_COLON.val)
      .toString();
    System.out.println(sql);

    PreparedStatement preSt;
    Connection conn = Datasource.getConn();
    try {
      preSt = conn.prepareStatement(sql);
      setSQLFieldValue(t, preSt);
      preSt.setString(columnName.size() + 1, id);
      preSt.executeUpdate();
      return true;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<T> search(String searchCondition) {
    String sql = new StringBuilder(StringSql.SELECT_CLAUSE.val).append(StringSql.SPACE.val)
      .append(tableName).append(StringSql.SPACE.val)
      .append(searchCondition)
      .toString();
    System.out.println(sql);

    PreparedStatement preSt;
    Connection conn = Datasource.getConn();
    try {
      preSt = conn.prepareStatement(sql);
      ResultSet resultSet = preSt.executeQuery();

      List<T> data = rowMapper(resultSet);
      if (!data.isEmpty()) {
        return data;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public List<T> search(String searchCondition, int limit, int offset) {
    String sql = new StringBuilder(StringSql.SELECT_CLAUSE.val).append(StringSql.SPACE.val)
      .append(tableName).append(StringSql.SPACE.val)
      .append(searchCondition).append(StringSql.SPACE.val)
      .append(StringSql.LIMIT.val).append(StringSql.SPACE.val)
      .append(StringSql.QUESTION_MARK.val).append(StringSql.SPACE.val)
      .append(StringSql.OFFSET.val).append(StringSql.SPACE.val)
      .append(StringSql.QUESTION_MARK.val).append(StringSql.SEMI_COLON.val)
      .toString();
    System.out.println(sql);

    PreparedStatement preSt;
    Connection conn = Datasource.getConn();
    try {
      preSt = conn.prepareStatement(sql);
      preSt.setInt(1, limit);
      preSt.setInt(2, offset);
      ResultSet resultSet = preSt.executeQuery();

      List<T> data = rowMapper(resultSet);
      if (!data.isEmpty()) {
        return data;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  private String getSQLParams() {
    StringBuilder sqlParam = new StringBuilder();
    sqlParam.append(StringSql.OPEN_PARENTHESIS.val);
    for (int i = 0; i < columnName.size(); i++) {
      sqlParam.append(StringSql.QUESTION_MARK.val);
      if (i < columnName.size() - 1) {
        sqlParam.append(StringSql.COMMA.val);
      }
    }
    sqlParam.append(StringSql.CLOSE_PARENTHESIS.val);
    return sqlParam.toString();
  }

  private String getSQLFieldList() {
    StringBuilder sql = new StringBuilder();
    sql.append(StringSql.OPEN_PARENTHESIS.val);
    for (int i = 0; i < columnName.size(); i++) {
      sql.append(columnName.get(i));
      if (i < columnName.size() - 1) {
        sql.append(StringSql.COMMA.val);
      }
    }
    sql.append(StringSql.CLOSE_PARENTHESIS.val);
    return sql.toString();
  }

  private void setSQLFieldValue(T t, PreparedStatement preSt) {
    Field field;
    for (int i = 0; i < columnFieldName.size(); i++) {
      try {
        field = clazz.getDeclaredField(columnFieldName.get(i));
        field.setAccessible(true);
        Object value = field.get(t);
        if (value != null) {
          preStSetValueFunc(preSt, i + 1, value);
        }
      } catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private void preStSetValueFunc(PreparedStatement preSt, int index, Object value) throws SQLException {
    if (value instanceof String) {
      preSt.setString(index, (String) value);
    } else if (value instanceof Integer) {
      preSt.setInt(index, (Integer) value);
    } else if (value instanceof Float) {
      preSt.setFloat(index, (Float) value);
    }
  }

  private String getSQLUpdateColumValue() {
    StringBuilder sqlSet = new StringBuilder();
    for (int i = 0; i < columnName.size(); i++) {
      sqlSet.append(columnName.get(i))
        .append(StringSql.EQUAL.val)
        .append(StringSql.QUESTION_MARK.val);
      if (i < columnName.size() - 1) {
        sqlSet.append(StringSql.COMMA.val);
      }
    }
    return sqlSet.toString();
  }
}
