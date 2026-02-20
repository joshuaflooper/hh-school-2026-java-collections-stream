package tasks;

import common.Person;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {

  public static List<Person> sort(Collection<Person> persons) {
    return persons.stream()
        .sorted((p1, p2) -> {
          if (p1.secondName().equals(p2.secondName()))
            if (p1.firstName().equals(p2.firstName()))
              return p1.createdAt().compareTo(p2.createdAt());
            else
              return p1.firstName().compareTo(p2.firstName());
          else
            return p1.secondName().compareTo(p2.secondName());
        })
        .collect(Collectors.toList());
  }
}
/*
Объявляю свой компаратор через лямбда-выражения.
Сравнение объектов происходит по следующему принципу.
Если фамилии равны, сравниваем имена, иначе сравниваем фамилии.
Если равны имена, сравниваем даты, иначе сравниваем имена.
Таким образом достигается нужный порядок при сортировке.
 */
