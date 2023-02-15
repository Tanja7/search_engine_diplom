import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    // Реализация поискового движка

    // Индексированные слова: ключ - слово, значение объект (искомый список)
    private final Map<String, List<PageEntry>> wordsIndexing = new HashMap<>();
    private final StopWords stopWords = new StopWords(new File("stop-ru.txt"));

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        // читаем тут все pdf и сохраняем нужные данные, тк во время поиска сервер не должен уже читать файлы
        File[] pdfFiles = pdfsDir.listFiles();
        if (pdfFiles != null) {
            // ищем в каждом файле
            for (File file : pdfFiles) {
                if (file.isFile()) {
                    // создание объекта документа
                    var doc = new PdfDocument(new PdfReader(file));
                    // поиск количества страниц
                    int countPages = doc.getNumberOfPages();
                    for (int i = 1; i <= countPages; i++) {
                        //создание объекта страницы
                        var page = doc.getPage(i);
                        //получение текста со страницы
                        var text = PdfTextExtractor.getTextFromPage(page);
                        // разбить текст на слова
                        var words = text.split("\\P{IsAlphabetic}+");
                        // подсчёт частоты слов
                        Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
                        for (var word : words) { // перебираем слова
                            if (word.isEmpty()) {
                                continue;
                            }
                            word = word.toLowerCase();
                            freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                        }
                        // добавление в индекс
                        for (var entry : freqs.entrySet()) {
                            addWord(entry.getKey(), file.getName(), i, entry.getValue());
                        }
                    }
                }
            }
        }
        // отсортируем списки
        for (var entry : wordsIndexing.entrySet()) {
            Collections.sort(entry.getValue());
        }
    }

    private void addWord(String word, String pdfName, int pageNumber, int count) {

        if (wordsIndexing.containsKey(word)) {
            var pages = wordsIndexing.get(word);
            pages.add(new PageEntry(pdfName, pageNumber, count));
        } else {
            wordsIndexing.put(word, new ArrayList<>());
            wordsIndexing.get(word).add(new PageEntry(pdfName, pageNumber, count));
        }
    }

    @Override
    public List<PageEntry> search(String text) {
        // Разбить текст на слова.
        var words = text.split("\\P{IsAlphabetic}+");
        SearchResult searchResult = new SearchResult();
        // Пройти по всем словам и попробовать найти слово
        for (var word : words) {
            word = word.toLowerCase();
            // Если слово в стоп листе, то пропустить.
            if (stopWords.contains(word))
                continue;
            // В результате будет общий список страниц, а количество найденных на каждой
            // странице слов будет суммироваться для каждого слова.
            if (wordsIndexing.containsKey(word)) {
                searchResult.addPages(wordsIndexing.get(word));
            }
        }
        //Сортируем результат
        searchResult.sort();
        return searchResult.getPages();
    }
}


