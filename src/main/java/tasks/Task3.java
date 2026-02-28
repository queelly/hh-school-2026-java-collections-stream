package tasks;

import common.Person;

import java.util.*;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {

  public static List<Person> sort(Collection<Person> persons) {
    // действительно, если есть null поля, упадет с ошибкой)
    return persons.stream()
            .filter(Objects::nonNull)
            .sorted(
                    Comparator.comparing(Person::secondName, Comparator.nullsLast(Comparator.naturalOrder()))
                            .thenComparing(Person::firstName, Comparator.nullsLast(Comparator.naturalOrder()))
                            .thenComparing(Person::createdAt, Comparator.nullsLast(Comparator.naturalOrder()))
            )
            .toList();
  }
}
