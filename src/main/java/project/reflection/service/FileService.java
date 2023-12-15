package project.reflection.service;

import java.util.List;

/**
 * @author : ad
 * @mailto : luunguyen301297@gmail.com
 * @created : 12/14/2023, Thursday
 **/
public interface FileService<T> {
  List<T> readFile(String path);

  List<T> search(String searchKeyword, String path);

  boolean add(T t, String path);

  boolean updateById(String id, T t, String path);

  boolean deleteById(String id, String path);
}
