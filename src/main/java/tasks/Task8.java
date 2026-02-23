package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;
import java.util.stream.Collectors;

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
    Set<Resume> resumes = personService.findResumes(persons.stream()
            .map(Person::id)
            .collect(Collectors.toSet()));
    Map<Integer, Set<Resume>> personResumesMap = new HashMap<>();
    resumes.forEach(r -> {
      if (personResumesMap.containsKey(r.personId()))
        personResumesMap.get(r.personId()).add(r);
      else
        personResumesMap.put(r.personId(), new HashSet<Resume>(Set.of(r)));
    });

    return persons.stream()
        .map(p -> new PersonWithResumes(p, personResumesMap.getOrDefault(p.id(), Collections.emptySet())))
        .collect(Collectors.toSet());
  }
}
/*
Не знаю, возсожно какое-то костыльное решение. Не придумал, как можно проще.
Методом тыка выяснил, что обработка вызовов всех методов, которые лежат в пакете common,
происходит вручную. То есть никакой прописанной логики нет. Поэтому не получится написать
personService.findResumes(set.of(person.id))), чтобы получить множество резюме конкретной персоны.
А так задача решалась бы в одну строчку. Короче, получаю множество всех резюме всех персон,
преобразуя коллекцию персон в множество их id через map. Потом создаю словарь id персоны/список его резюме.
Прохожусь по полученному ранее множеству, смотрю на id персоны-владельца резюме.
Если я уже добавлял для него резюме в словарь, то просто к соответствующему множеству добавляю это резюме,
иначе создаю новое множесвто из этого резюме. Потом через map превращаю коллекцию персон в множество объектов
персона + множество её резюме. Используюю getOrDefault, чтобы получить не null, а пустое множество
в случае, если персоны нет в словаре.
 */
