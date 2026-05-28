import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TopWords {
    public static void main(String[] args) {
        Map<String, Integer> wordMap = new HashMap<>();

        try {
            Scanner scanner = new Scanner(new File("ttt.txt"), "UTF-8");

            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                word = word.replaceAll("[^a-zA-Z]", "");

                if (word.isEmpty()) {
                    continue;
                }

                if (wordMap.containsKey(word)) {
                    wordMap.put(word, wordMap.get(word) + 1);
                } else {
                    wordMap.put(word, 1);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
            return;
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                return b.getValue() - a.getValue();
            }
        });

        System.out.println("Топ-10 самых частых слов:");
        for (int i = 0; i < 10 && i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getKey() + " - " + list.get(i).getValue());
        }
    }
}
