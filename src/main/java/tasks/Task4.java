package tasks;

import common.ApiPersonDto;
import common.Person;
import common.PersonConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
Задача 4
Список персон класса Person необходимо сконвертировать в список ApiPersonDto
(предположим, что это некоторый внешний формат)
Конвертер для одной персоны - personConverter.convert()
FYI - DTO = Data Transfer Object - распространенный паттерн, можно погуглить
 */
public class Task4 {

  private final PersonConverter personConverter;

  public Task4(PersonConverter personConverter) {
    this.personConverter = personConverter;
  }

  public List<ApiPersonDto> convert(List<Person> persons) {
    return persons.stream()
        .map(personConverter::convert)
        .collect(Collectors.toList());
  }
}
/*
Делаю map функции конвертирования для стрима.
Для стрима по сути не важно, объекты какого типа в нём лежат (я так понял).
Поэтому все Person без проблем превратились в ApiPersonDto. Потом просто собрал стрим в список.
 */
