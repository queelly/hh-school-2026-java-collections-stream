package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {
    Set<Resume> resumes = personService.findResumes(persons.stream().map(Person::id).toList());
    Set<PersonWithResumes> personWithResumesSet = new HashSet<>();
    for (Person person : persons) {
      personWithResumesSet.add(new PersonWithResumes(
              person, personService.findResumes(Collections.singletonList(person.id()))));
    }
    return personWithResumesSet;
  }
}
