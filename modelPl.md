Model

Zakładamy, że epidemia rozwija się w populacji agentów, którzy zorganizowani są w sieć społecznościową zdefiniowaną jako graf nieskierowany. Symulacja zaczyna się od wylosowania tego grafu tak żeby średnia liczba znajomych była maksymalnie zbliżona do wartości śrZnajomych (tzn. żadna inna liczba krawędzi nie dawała innego lepszej średniej). W chwili obecnej rozważamy dwa rodzaje agentów: zwykłych i towarzyskich. Rodzaj agenta określany jest przez losowanie przy użyciu parametru prawdTowarzyski. Agenci w populacji mogą być zdrowi, zarażeni lub mieć odporność (są to grupy rozłączne). Wszyscy poza jednym wylosowanym agentem zaczynają jako zdrowi (bez odporności), a jeden zaczyna jako zarażony.

Na początku każdego dnia każdy zarażony agent może umrzeć (z prawd. śmiertelność) lub wyzdrowieć (z prawd. prawdWyzdrowienia). Agent, który umarł przestaje uczestniczyć w symulacji, a agent który wyzdrowiał nabiera odporność i już nigdy nie zachoruje.

Każdego dnia symulacji każdy agent może umawiać się na spotkanie z innymi agentami. Agent z prawd. prawdSpotkania decyduje czy chce się spotkać i jeżeli tak to losuje jednego ze swoich znajomych w przypadku agenta zwykłego lub ze swoich znajomych i znajomych swoich znajomych w przypadku agenta towarzyskiego (można planować spotkania i spotykać się z tym samym agentem wiele razy danego dnia). Następnie agent losuje jeden z pozostałych dni symulacji kiedy do takiego spotkania dojdzie. Agent powtarza planowanie spotkań dopóki nie wylosuje, że nie chce się spotykać.

Gdy już wszystkie nowe spotkania zostały zaplanowane, to dochodzi spotkań przypadających na dany dzień. Jeżeli któryś ze spotykających się agentów jest zarażony a drugi nie ma odporności, to z prawd. prawdZarażenia może dojść do zarażenia, wpp. takie spotkanie nie ma żadnego efektu. Zarażenie agenta ma następujące konsekwencje. Jeżeli to agent zwykły to dopóki nie wyzdrowieje będzie planował nowe spotkania z dwa razy mniejszym prawdopodobieństwem. Jeżeli to agent towarzyski to dopóki nie wyzdrowieje będzie planował się spotykać tylko ze swoimi bezpośrednimi znajomymi (nie ma to wpływu na to czy inni będą się decydować spotykać z nim i na spotkania, które już zaplanowano).


Format wyniku:
# twoje wyniki powinny zawierać te komentarze
seed=...
liczbaAgentów=...
prawdTowarzyski=...
prawdSpotkania=...
prawdZarażenia=...
prawdWyzdrowienia=...
śmiertelność=...
liczbaDni=...
śrZnajomych=...
 
# agenci jako: id typ lub id* typ dla chorego
1 zwykły
2 zwykły
3 towarzyski
4* zwykły
5 towarzyski
6 zwykły
 
# graf
1 5 2
2 3 4 1
3 2 4
4 2
5 1
 
# liczność w kolejnych dniach
zdrowi1 chorzy1 uodp1
zdrowi2 chorzy2 uodp2
zdrowi3 chorzy3 uodp3
...