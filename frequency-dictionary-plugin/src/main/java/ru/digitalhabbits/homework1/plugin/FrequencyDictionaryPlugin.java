package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        text = text.toLowerCase();
        text = text.replaceAll("[()?!,;.-]", "");
        String[] words;
        words = text.split("\\s");

        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.compute(word, (k, v)
                    -> v == null ? 1 : v + 1);
        }
        Map<String, Integer> sortedMapDesc = sortByValue(map, false);
        text = printMap(sortedMapDesc);
        return text;

    }

    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap, final boolean order) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

    private static String printMap(Map<String, Integer> map) {
        StringBuilder s = new StringBuilder();
        for (String name : map.keySet()) {
            int value = map.get(name);
            s.append(name).append(" ").append(value).append("\n");
            //s = s + value + " " + key + "\n";
        }
        return s.toString();
    }
}

