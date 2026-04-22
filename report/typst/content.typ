= Цель работы
найти приближенное значение определенного интеграла с требуемой точностью различными численными методами.

= Порядок выполнения работы

Лабораторная работа состоит из 2 частей: вычислительной и программной.

== Программная реализация задачи

+ Реализовать в программе методы по выбору пользователя:

  - Метод прямоугольников (3 модификации: левые, правые, средние)
  - Метод трапеций
  - Метод Симпсона

+ Методы должны быть оформлены в виде отдельной(ого) функции/класса.

+ Вычисление значений функции оформить в виде отдельной(ого) функции/класса.

+ Для оценки погрешности и завершения вычислительного процесса использовать правило Рунге.

+ Предусмотреть вывод результатов: значение интеграла, число разбиения интервала интегрирования для достижения требуемой точности.

== Вычислительная реализация задачи

$
  integral_(-3)^(-1) (-3x^3 - 5x^2 + 4x - 2) dif x
$

+ Вычислить интеграл точно.

+ Вычислить интеграл по формуле Ньютона -- Котеса при $n = 6$.

+ Вычислить интеграл по формулам средних прямоугольников, трапеций и Симпсона при $n = 10$.

+ Сравнить результаты с точным значением интеграла.

+ Определить относительную погрешность вычислений для каждого метода.

+ В отчете _*отразить последовательные вычисления*_.

= Рабочие формулы методов
Пусть $h = h_i = (b - a) / n$.

== Формула Ньютона -- Котеса

$
  integral_a^b f(x) dif x approx integral_a^b L_n(x) dif x = sum_(i = 0)^n f(x_i) c_n^i
$
Коэффициенты Котеса для $n = 6$:
$
  c_6^0 = c_6^6 = (41(b - a)) / 840 \
  c_6^1 = c_6^5 = (216(b - a)) / 840 \
  c_6^2 = c_6^4 = (27(b - a)) / 840 \
  c_6^3 = (272(b - a)) / 840
$

== Методы прямоугольников
+ Левых:
$
  integral_a^b f(x) dif x = h sum_(i=0)^(n-1) f(x_i) 
$
+ Правых:
$
  integral_a^b f(x) dif x = h sum_(i=1)^(n) f(x_i) 
$
+ Средних:
$
  integral_a^b f(x) dif x = h sum_(i=1)^(n) f(x_(i - 1) + h/2)
$

== Метод трапеций

$
  integral_a^b f(x) dif x = h ((f(a) + f(b)) / 2 + sum_(i = 1)^(n - 1) f(x_i))
$

== Метод Симпсона

$
  integral_a^b f(x) dif x =h/3 (f(a) + 4(f(x_1) + f(x_3) + ... + f(x_(n - 1))) + \ + 2(f(x_2) + f(x_4) + ... + f(x_(n - 2)) ) + f(b))
$

= Листинг программы

#raw(lang: "java", read("../../src/main/java/ru/astrosoup/models/integrals/IntegralCalculationHandler.java"), block: true)

= Пример результатов выполнения программы
Ввод с консоли:

```
astrosoup@astrosoup-laptop:~/computational-mathematics-lab-3$ java -jar build/libs/computational-mathematics-lab-3-0.0.jar 
Please input the function that you want to calculate integral of (use "x" as a variable name):
f(x) = -3 * x^3 - 5 * x^2 + 4 * x - 2 
Please input the lower bound for integral calculation:
a = -3
Please input the higher bound for integral calculation:
b = -1
Please input the accuracy of calculation:
eps = 0.001
Please input the selected method for calculating the integral left rectangle method(1), middle rectangle method(2), right rectangle method(3), trapezoid method(4), simpson method(5):
m = 2
The resulting integral is: -3.3313801884796717
```
Ввод из файла:

```
astrosoup@astrosoup-laptop:~/computational-mathematics-lab-3$ java -jar build/libs/computational-mathematics-lab-3-0.0.jar test
For file "test": 
The resulting integral is: -3.3320311506686275
```

Справка:
```
astrosoup@astrosoup-laptop:~/computational-mathematics-lab-3$ java -jar build/libs/computational-mathematics-lab-3-0.0.jar --help
Any argument to the program except "--help" is treated as a file path. If no arguments are given the input is taken from console.

For a file to be a valid input it must follow <name>=<value> argument structure. It also must include the following parameters:
    a       lower integration bound 
    b       higher integration bound 
    eps     accuracy of calculation needed 
    f       function that needs to be integrated (currently program only works for one variable functions with a variable of name x). 
    m       a numerical method with which the integral should be calculated (currently program supports left(1), middle(2) or right(3) rectangle methods, trapezoid(4) method and simpson(5) method).
    To input the method write the number corresponding to it.
```

= Вычисление заданного интеграла
== Точное вычисление интеграла
#let evaluated(expr, size: 100%) = $lr(#expr|, size: #size)$

$
  integral_(-3)^(-1) (-3x^3 - 5x^2 + 4x - 2) dif x = evaluated((-3 dot (x^4) / 4 - 5 dot (x^3)/ 3 + 4 dot (x^2) / 2 - 2x))_(-3) ^ (-1) = \ = (-3 dot 1/4 - 5 dot (-1)/3 + 4 dot 1/2 - 2 dot (-1))-(-3 dot 81 / 4 - 5 dot (-27) / 3 + 4 dot 9 / 2 - 2 dot (-3)) = \ = -3/4 + 5/3 + 2 + 2 +243/4 - 135/3 - 18 - 6 = -10/3
$
== Вычисление по формуле Ньютона -- Котеса
Шаг:
$
h = ( -1 - (-3) ) / 6 = 1/3
$

Таблица значений:

#table(
  columns: (1fr, 1fr, 1fr),
  inset: 8pt,
  align: horizon+center,
  [*i*], [*$x_i$*], [*$f(x_i)$*],
  [0], [-3], [22],
  [1], [$-8/3$], [$26/3$],
  [2], [$-7/3$], [$-4/9$],
  [3], [-2], [-6],
  [4], [$-5/3$], [$-26/3$],
  [5], [$-4/3$], [$-82/9$],
  [6], [-1], [-8],
)

$
integral_(-3)^(-1) (-3x^3 - 5x^2 + 4x - 2) dif x = (41((-1) - (-3))) / 840 dot 22 + (216((-1) - (-3))) / 840 dot 26/3 + \ + (27((-1) - (-3))) / 840 dot (-4/9) + (272((-1) - (-3))) / 840 dot (-6) + (27((-1) - (-3))) / 840 dot (-26/3) + \ + (216((-1) - (-3))) / 840 dot (-82/9) + (41((-1) - (-3))) / 840 dot (-8) = \ = 451/210 + 156/35 - 1/35 - 136/35 -39/70 -164/35 -82/105 = -10/3
$
== Вычисление по заданным формулам
$
  h = ((-1) - (-3)) / 10 = 1/5
$
=== Вычисление по формуле средних прямоугольников
$
  integral_(-3)^(-1) (-3x^3 - 5x^2 + 4x - 2) dif x = 1/5 sum_(i = 1)^8 f(x_(i-1) + 1/10) = \ = 17517/1000 + 9799/1000 + 29/8 - 1149/1000 - 4667/1000 - 7073/1000 - 8511/1000 - 73/8 - 9059/1000 - 8457/1000 = \ = -17.1
$

=== Вычисление по формуле трапеций
$
  integral_(-3)^(-1) (-3x^3 - 5x^2 + 4x - 2) dif x = 1/5 ((f(-3) + f(-1)) / 2 + sum_(i = 1)^(9) f(x_i)) = \ = 13.456 + 6.528 + 1.072 - 3.056 - 6
- 7.904 - 8.912 - 9.168 - 8.816 = -22.8
$

=== Вычисление по формуле Симпсона

$
  integral_(-3)^(-1) (-3x^3 - 5x^2 + 4x - 2) dif x = \ = 1/15 (f(-3) + 4(f(-3 + 1/5) + f(-3 + 3/5) + f(-3 + 5/5) + f(-3 + 7/5) + f(-3 + 9/5))) + \ + 2(f(-3 + 2/5) + f(-3 + 4/5) + f(-3 + 6/5) + f(-3 + 8/5)) + f(-1)) = \ = -3.33
$

== Сравнение численных и точного результатов

Можем заметить что формула Ньютона -- Котеса и формула Симпсона совпали с точным значением интеграла, а формула прямоугольников и трапеций нет. Так произошло из-за того что формула прямоугольников и трапеций являются методами 2 порядка точности, в отличие от формулы Симпсона (4 порядок) и формулы Ньютона -- Котеса (6 порядок).

= Вывод
В ходе выполнения лабораторной работы я научился численному вычислению интегралов и попробовал реализовать их программно.