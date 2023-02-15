import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class StopWords {
    // создание множества из стоп-слов
    private final Set<String> ignoredWords = new HashSet<>();

    public StopWords(File textFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            reader.lines().forEach(ignoredWords::add);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean contains(String word) {
        return ignoredWords.contains(word);
    }
}

