# Дипломная работа. Поисковая система

Разработка класса поискового движка, который способен быстро находить указанное слово среди pdf-файлов, причём ранжировать результаты по количеству вхождений. 
А также создание сервера, который обслуживает входящие запросы с помощью этого движка.

В папке проекта присутсвует папка `pdfs` - в ней находятся .pdf-файлы, по которым ищет поисковый движок.


| Класс                 | Описание                                                                                                                                                  |
|-----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| *Main*                | Запуск сервера, обслуживающего поисковые запросы                                                                                                          |
| *PageEntry*           | Класс, описывающий один элемент результата одного поиска.                                                                                                 |
| *SearchEngine*        | Интерфейс, описывающий поисковый движок. Всё что должен уметь делать поисковый движок, это на запрос из слов отвечать списком элементов результата ответа |
| *BooleanSearchEngine* | Реализация поискового движка. Движок ищет в тексте ровно то слово, которое было указано, без использования синонимов и прочих приёмов нечёткого поиска.   |
| *SearchResult*        | Определение суммарного количество раз, которое встретилось любое из слов запроса.                                                                         |
| *StopWords*           | Список слов, никак не влияющих на запрос, т.к. их встречаемость в запросе никакой информационной нагрузки не несёт.                                       |
| *Client*              | Отправка запросов на сервер.                                                                                                                              |

## Индексация и поиск
В начале работы производится _индексация_ (сканирование) всех pdf-файлов с сохранением информации для каждого слова из них. При этом списки ответов для каждого слова сортируются в порядке уменьшения количества раз.
Затем запускается сервер, к которому происходят подключения и на входной поток подается запрос слов.
Результат ответа сервера выходит в виде JSON-текста.

