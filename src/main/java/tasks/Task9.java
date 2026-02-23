package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    // на мой взгляд тут этот метод более читаемый, чем size
    if (persons.isEmpty()) {
      return Collections.emptyList();
    }
    // лучше сделать все операции внутри стрима и не менять исходные данные
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    // проще просто через конструктор сета собрать новое множество и так без повторений,
    // код в разы короче без стрима, кстати дистинкт был лишним в старом коде
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    // проще в 1 строку склеить фио через стрим, а так же проверять на null
    return Stream.of(person.firstName(), person.secondName(), person.middleName()).filter(
            name -> name != null && !name.isEmpty()).collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // проще так же в 1 строку собрать в мапу коллекцию персонов
    return persons.stream().collect(
            Collectors.toMap(Person::id, this::convertPersonToString));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // асимптотика из квадрата в линию, в одну строку с помощью стрима
    return persons1.stream().anyMatch(new HashSet<>(persons2)::contains);
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    // убрал использование поля в классе, еще проще использовать просто count от стрима
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    // это верно, рассмотрим вызов метода toString для нашего сета и нашего листа:
    // в листе строка это просто [1, 2, 3, ... , 10000].
    // теперь рассмотрим сет - это просто обертка над хэш-таблицей,
    // в ключах лежат сами элементы, а в значениях заглушки,
    // а у Integer в диапазоне от 1 до 10000 хэш-кодом выступает само число,
    // поэтому когда toString будет проходить сет в порядке увеличения ключей,
    // он будет просто писать числа от 1 до 10000 в порядке увеличения,
    // поэтому совпадают и вызовы toString, такая вот интересная ситуация
    assert snapshot.toString().equals(set.toString());
  }
}
