package project.reflection.test_product;

import org.apache.commons.lang3.StringUtils;
import project.reflection.service.impl.FileServiceImpl;

/**
 * @author : ad
 * @mailto : luunguyen301297@gmail.com
 * @created : 12/14/2023, Thursday
 **/
public class FileRepository extends FileServiceImpl<Product> {
  public FileRepository() {
    super(Product.class);
  }

  @Override
  protected Product lineMapper(String header, String line) {
    if (StringUtils.isEmpty(line))
      return null;
    if (line.trim().equalsIgnoreCase(header))
      return null;
    String[] chars = line.split("\\|");

    return Product.builder()
      .id(Long.parseLong(chars[0]))
      .name(String.valueOf(chars[1]))
      .producer(String.valueOf(chars[2]))
      .productLine(String.valueOf(chars[3]))
      .price(Float.parseFloat(chars[4]))
      .build();
  }
}
