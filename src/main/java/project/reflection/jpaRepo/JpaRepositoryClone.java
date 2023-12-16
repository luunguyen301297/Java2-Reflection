package project.reflection.jpaRepo;

import java.util.List;

public interface JpaRepositoryClone<T> {
  T getById(String id);

  List<T> getAll();

  List<T> getAll(int limit, int offset);

  boolean insert(T t);

  boolean insertBatch(List<T> tList);

  boolean deleteById(String id);

  boolean updateById(String id, T t);

  List<T> search(String searchCondition);

  List<T> search(String searchCondition, int limit, int offset);
}
