package tasks;

import common.Company;
import common.Vacancy;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 {

  public static Set<String> vacancyNames(Collection<Company> companies) {
    return companies.stream()
        .flatMap(company -> company.getVacancies().stream()
            .map(Vacancy::getTitle))
        .collect(Collectors.toSet());
  }
}
/*
Решение практически аналогично предыдущей задаче.
Использую flatMap, чтобы для каждой компании в стриме развернуть стрим вакансий,
преобразую его в стрим названий вакансий, потом собираю эти стримы в один (суть flatMap как раз таки),
собираю стрим в множество. По сути при сборе в множество все повторы убираются и получается действительно множество.
 */
