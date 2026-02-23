package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {

  public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                     Collection<Person> persons2,
                                                     int limit) {
    List<Person> returnList = new ArrayList<>(persons1.stream().toList());
    returnList.addAll(persons2);
    returnList.sort(Comparator.comparing(Person::createdAt));
    return returnList.subList(0, Integer.min(Integer.max(limit, 0), returnList.size()));
  }
}
