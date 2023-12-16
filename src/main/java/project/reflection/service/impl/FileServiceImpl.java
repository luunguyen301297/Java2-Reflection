package project.reflection.service.impl;

import lombok.Data;
import project.reflection.service.FileService;
import project.reflection.test_product.FileRepository;
import project.reflection.test_product.Product;
import project.reflection.utility_class.AnnotationAndField;
import project.reflection.utility_class.StringHeaderUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

  protected abstract String formatObjToFileLIne(T t);

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
    for (T t : allObjectInFile) {
      Field[] fields = clazz.getDeclaredFields();
      for (Field f : fields) {
        f.setAccessible(true);
        try {
          Object fValue = f.get(t);
          if (fValue != null && fValue.toString().toLowerCase().contains(searchKeyword.toLowerCase())) {
            searchResultList.add(t);
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
  public boolean addToFile(T t, String path) {
    try {
      Field field = t.getClass().getDeclaredField(fieldName.get(0));
      field.setAccessible(true);
      Object newValue = field.get(t);

      List<T> allObjectsInFile = readFile(path);
      for (T obj : allObjectsInFile) {
        Field objField = obj.getClass().getDeclaredField(fieldName.get(0));
        objField.setAccessible(true);
        Object value = objField.get(obj);
        if (value.equals(newValue)) {
          return false;
        }
      }

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
        writer.write(formatObjToFileLIne(t));
        writer.newLine();
        return true;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean updateById(String id, T t, String path) {
    List<String> lines = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.contains(id)) {
          lines.add(formatObjToFileLIne(t));
        } else {
          lines.add(line);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("An error occurred while reading the file");
    }
    safeWriteToFile(lines, path);
    return true;
  }

  @Override
  public boolean deleteById(String id, String path) {
    List<String> lines = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (!line.contains(id)) {
          lines.add(line);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("An error occurred while reading the file");
    }
    safeWriteToFile(lines, path);
    return true;
  }

  @Override
  public boolean createNewFile(String path) {
    Path newFilePath = Paths.get(path);
    if (!Files.exists(newFilePath)) {
      try {
        Files.createFile(newFilePath);
        System.out.println("File was created with path : " + path);
        return true;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      System.err.println("File already exists !");
      return false;
    }
  }

  private void safeWriteToFile(List<String> lines, String path) {
    Path tempFilePath = Paths.get(path + ".tmp");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilePath.toFile()))) {
      for (String line : lines) {
        writer.write(line);
        writer.newLine();
      }
    } catch (IOException e) {
      throw new RuntimeException("An error occurred while writing to the temporary file");
    }

    // Xóa file gốc
    Path originalFilePath = Paths.get(path);
    try {
      Files.deleteIfExists(originalFilePath);
    } catch (IOException e) {
      throw new RuntimeException("Failed to delete the original file");
    }

    // Đổi tên tệp .tmp thành tên của file gốc
    try {
      Files.move(tempFilePath, originalFilePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException("Failed to rename the temporary file to the original file name.");
    }
  }

  public static void main(String[] args) {
    FileRepository repo = new FileRepository();
    String path = "C:/Users/ad/IdeaProjects/Java2_Reflection/etc/product_table.txt";

    Product p = Product.builder()
      .id(10)
      .name("test update")
      .producer("test update")
      .productLine("test update")
      .price(10000000.0f)
      .build();

    long startTime = System.currentTimeMillis();

//    repo.readFile(path).forEach(System.out::println);
//    System.out.println(repo.addToFile(p, path));
//    System.out.println(repo.updateById("10", p, path));
//    System.out.println(repo.deleteById("10", path));
    System.out.println(repo.search("Iphone 5", path));

    System.out.println("time : " + (System.currentTimeMillis() - startTime));
  }
}
