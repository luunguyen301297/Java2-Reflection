package project.reflection.service.impl;

import lombok.Data;
import project.reflection.service.FileService;
import project.reflection.test_product.FileRepository;
import project.reflection.utility_class.AnnotationAndField;
import project.reflection.utility_class.StringHeaderUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

@Data
public abstract class FileServiceImpl<T> implements FileService<T> {
  private Class<?> clazz;
  private List<String> fieldName;
  AnnotationAndField annotationAndField;
  private String header;

  public FileServiceImpl(Class<?> clazz) {
    this.clazz = clazz;
    this.annotationAndField = new AnnotationAndField(clazz);
    this.fieldName = annotationAndField.getAllFieldName();
    this.header = StringHeaderUtils.headerBuilder(getFieldName());
  }

  protected abstract T lineMapper(String header, String line);

  @Override
  public List<T> readFile(String path) {
    List<T> tList = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = reader.readLine()) != null) {
        T t = lineMapper(getHeader(), line);
        if (t != null) {
          tList.add(t);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found! " + e.getMessage());
    } catch (IOException e) {
      System.err.println("IOException " + e.getMessage());
    }
    return tList;
  }

  @Override
  public List<T> search(String searchKeyword, String path) {
    List<T> searchResultList = new ArrayList<>();
    List<T> allObjectInFile = readFile(path);

    int count = 0;
    for (T obj : allObjectInFile) {
      Field[] fields = clazz.getDeclaredFields();
      for (Field f : fields) {
        f.setAccessible(true);
        try {
          Object fValue = f.get(obj);
          if (fValue != null && fValue.toString().contains(searchKeyword)) {
            searchResultList.add(obj);
            count++;
            break;
          }
        } catch (IllegalAccessException e) {
          throw new RuntimeException();
        }
      }
    }
    return count == 0 ? null : searchResultList;
  }

  @Override
  public boolean add(T t, String path) {
    FileWriter myWriter;
    try {
      myWriter = new FileWriter(path, true);
      myWriter.write(t.toString());
      myWriter.close();
      return true;
    } catch (IOException e) {
      System.err.println(e.getMessage() + " / An error occurred !");
    }
    return false;
  }

  @Override
  public boolean updateById(String id, T t, String path) {
    return false;
  }

  @Override
  public boolean deleteById(String id, String path) {
    return false;
  }

  private boolean createNewFile() {
    String path = "";
    File newFile;
    boolean checkExists = false;
    try {
      newFile = new File(path);
      if (!newFile.exists()) {
        newFile.createNewFile();
        System.out.println("File was created with path : " + path);
        checkExists = true;
      } else {
        System.err.println("File already exists !");
      }
    } catch (IOException e) {
      System.err.println(e.getMessage() + " / An error occurred !");
    }
    return checkExists;
  }

  public static void main(String[] args) {
    FileRepository repo = new FileRepository();
    String path = "C:/Users/ad/IdeaProjects/Java2_Reflection/etc/product_table.txt";
    System.out.println(repo.getHeader());

    long startTime = System.currentTimeMillis();

//    repo.readFile(path).forEach(System.out::println);
    System.out.println(repo.search("iphone 3", path));

    System.out.println("time : " + (System.currentTimeMillis() - startTime));
  }
}
