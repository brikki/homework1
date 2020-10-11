package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: Не доделана регулярка (\b[a-zA-Z][a-zA-Z.0-9]*\b)
        //  Способы реализации: регулярки, стрим (flatmap или стрим статистик), масиивы
        int lineCount, charCount;
        int wordCount = 0;
        String[] str = text.split("\\W");
        String[] str1 = text.split(("\n"));
        lineCount = str1.length;
        charCount = text.length();
        for (String s : str) {
            if (s.length() > 0) {
                wordCount++;
            }
        }
        return lineCount + ";" + wordCount + ";" + charCount + ";";
    }
}
