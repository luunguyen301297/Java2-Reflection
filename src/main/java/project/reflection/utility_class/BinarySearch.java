package project.reflection.utility_class;

import java.util.Arrays;
import java.util.List;

/**
 * @author : ad
 * @mailto : luunguyen301297@gmail.com
 * @created : 12/15/2023, Friday
 **/
public class BinarySearch {
  public static <T extends Comparable<T>> int binarySearch(List<T> list, T key) {
    int low = 0;
    int high = list.size() - 1;

    while (low <= high) {
      int mid = (low + high) / 2;
      T midValue = list.get(mid);
      int cmp = midValue.compareTo(key);

      if (cmp < 0) {
        low = mid + 1;
      } else if (cmp > 0) {
        high = mid - 1;
      } else {
        return mid;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
    int index = binarySearch(list, 6);
    if (index != -1) {
      System.out.println("index : " + index);
    } else {
      System.err.println("err");
    }
  }
}
