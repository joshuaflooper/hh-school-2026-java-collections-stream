package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объединить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {

  public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                     Collection<Person> persons2,
                                                     int limit) {
    List<Person> result = new ArrayList<>();
    result.addAll(persons1);
    result.addAll(persons2);
    result.sort(Comparator.comparing(Person::createdAt));
    return result.stream()
        .limit(limit)
        .collect(Collectors.toList());
  }
}
/* Создаю новый список, добавляю в его конец сначала элементы коллекции persons1,
а потом элементы коллекции persons2. Сортирую по дате создания через Comparator.comparing.
Возвращаю через стрим список из первых limit элементов.
 */
