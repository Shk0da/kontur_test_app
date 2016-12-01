package local.shkondin.dev.core;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Основной класс для работы со словарем и поиском автодополнений.
 */
public class Searcher {

    private static final String testIn = "src/main/resources/test.in";
    private static ConcurrentHashMap<String, Searcher> multitons = new ConcurrentHashMap<>();
    private TreeMap<String, Integer> alphabet = new TreeMap<>();
    private HashMap<String, String> register = new HashMap<>();

    public static Searcher getInstance(final String path) {
        return multitons.computeIfAbsent(path, searcher -> new Searcher(path));
    }

    private Searcher(String path) {
        path = path.isEmpty() ? testIn : path;

        try {
            setAlphabet(path);
        } catch (IOException e) {
            System.out.println("Не смогли загрузить тестовые данные");
            System.exit(0);
        }
    }

    /**
     *
     * @param fileName путь до файла словаря
     * @throws IOException в случае не корректного словаря
     */
    private void setAlphabet(String fileName) throws IOException {
        Files.lines(Paths.get(fileName), StandardCharsets.UTF_8).forEach((row) -> {
            String[] split = row.split(" ");
            if (split.length == 2) {
                String word = split[0];
                Integer count = Integer.parseInt(split[1]);
                alphabet.put(word, count);
            }
        });
    }

    /**
     * Поиск в заданном словаре автодополнений для префикса
     * @param query префикс
     * @return список из до 10 самых популярных выражений
     */
    public String find(String query) {
        if (!register.containsKey(query)) {
            List<Map.Entry<String, Integer>> sorted = new LinkedList<>();
            sorted.addAll(alphabet.subMap(query, query + Character.MAX_VALUE).entrySet());
            sorted.sort((Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) -> b.getValue() - a.getValue());

            int count = 10;
            StringBuilder result = new StringBuilder();
            for (Map.Entry<String, Integer> word : sorted) {
                result.append(word.getKey()).append("\n");
                if (count-- == 0) break;
            }

            register.put(query, result.toString());
        }

        return register.get(query);
    }

}
