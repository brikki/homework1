package ru.digitalhabbits.homework1.service;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.Arrays.stream;

public class FileEngine {
    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = "txt";
    private final String userDir = System.getProperty("user.dir");

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) {

        final File completeFileName = new File(userDir + "/" + RESULT_DIR + "/" + String.format(RESULT_FILE_PATTERN, pluginName));
        //System.out.println(completeFileName);
        try (BufferedWriter writeToFile = new BufferedWriter(new FileWriter(completeFileName))) {
            writeToFile.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void cleanResultDir() {
        final File resultDir = new File(userDir + "/" + RESULT_DIR);
        stream(resultDir.list((dir, name) -> name.endsWith(RESULT_EXT)))
                .forEach(fileName -> new File(resultDir + "/" + fileName).delete());
    }
}
