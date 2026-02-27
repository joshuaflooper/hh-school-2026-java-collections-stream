package tasks;

import common.Person;

import java.util.*;
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
    return persons.stream()
            .map(Person::firstName)
            .skip(1)
            .collect(Collectors.toList());
  }
  // 1) Так как коллекции передаются по ссылке, то удаление
  // из persons приведёт к удалению везде, а не только внутри метода.
  // Не в курсе внутреннего устройства эластика, но вдруг эта первая
  // персона ещё пригодится. К тому же, стримах есть метод .slip(n) —
  // использую его.
  // 2) Выкидываем проверку на пустоту списка персон. Если он будет пустым,
  // то поток тоже будет пустым — map и skip ничего не сделают, получится пустой список.
  // (Я проверил).

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }
  // Ну тут масло масляное. Во-первых, distinct не нужен, так как при
  // преобразовании в множество все повторения по-любому удалятся.
  // Во-вторых, тогда уж и стрим лишний, потмоу что можно
  // преобразовать в множество просто через конструктор.

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    return Stream.of(person.firstName(), person.middleName(), person.secondName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }
  // Сделал стрим из соответствующих полей персоны.
  // Отфильтровал по функции nonNull, применил коллектор joining,
  // позволяющий склеить строки с разделителем.

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(Person::id, this::convertPersonToString));
  }
  // А, эм... Мне кажется, лучше использовать стрим. Получится в одну строчку.
  // Касательно того, что есть. Во-первых, не понятно, зачем начальны размер 1.
  // А если коллекция персон пустая? Во-вторых, не понятно, зачем нужна проверка
  // на содержание ключа. Как будто лучше просто перезаписывать, учитывая,
  // что связка id + имя уникальна. Короче, много строчек кода -> одна строчка (ну почти).

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    for (Person person1 : persons1)
      for (Person person2 : persons2)
        if (person1.equals(person2))
          return true;
    return false;
  }
  // Во-первых, оптимальнее будет сразу вернуть true, когда найдутся одинаковые персоны.
  // Во-вторых, можно использовать стримы (filter persons1 по persons2::contains и потом проверяем,
  // не пуст ли получившийся стрим, — такой вот математический подход). Но это точно будет работать всегда за O(m*n),
  // так как будет происходить полный перебор для пересечения. Плюс расход памяти будет больше.
  // А тут метод завершится при нахождении первого совпадения. То есть O(m*n) только в худшем случае.

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }
  // А зачем переменной count быть глобальной?... Зачем вообще она нужна и зачем подсчёт через forEach?
  // У стримов же есть count!

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
  // Я, честно говоря, не уверен. Но мне кажется, что хэш у чисел — это само значение числа.
  // Получается, что хэш (номер) бакета равен значению числа, а так как бакеты хранятся по порядку,
  // то и сами числа оказываются расставленными по порядку.
