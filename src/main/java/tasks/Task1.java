package tasks;

import common.Person;
import common.PersonService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    Map<Integer, Person> personMap = persons.stream()
        .collect(Collectors.toMap(Person::id, p -> p));
    return personIds.stream()
        .map(personMap::get)
        .collect(Collectors.toList());
  }
}
/*
Всё просто. Через стрим преобразую получаемое множество людей в словарь,
в качестве ключа беру id человека. Потом тоже через стрим преобразую входной список id
в список людей, сопоставляя каждому id человека по словарю. Словарь создаётся за O(N),
так как происходит проход по каждому элементу множества из N элементов (вставка при этом за O(1)).
Преобразование списка происходит за O(M), так как также происходит проход по каждому элементу списка
из M элементов (замена при этом также за O(1)). Итого: O(M+N).
 */
