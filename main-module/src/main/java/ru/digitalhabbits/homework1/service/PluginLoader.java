package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

import static com.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = "jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName)  {
        final List<Class<? extends PluginInterface>> loadPlugins = new ArrayList<>();
        File filePlugDir = new File(pluginDirName);
        File[] jarFiles = filePlugDir.listFiles((dir, filename) -> filename.endsWith("." + PLUGIN_EXT));
        if (jarFiles != null && jarFiles.length > 0) {
            ArrayList<URL> urls = new ArrayList<>(jarFiles.length);
            ArrayList<String> classes;

            for (File file : jarFiles) {
                    classes = getClassAndAddArrayList(file);
                URL url = null;
                try {
                    url = file.toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                urls.add(url);
                URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[0]));
                classes.forEach(className -> {
                    try {
                        Class loadClass = urlClassLoader.loadClass(className.replaceAll("/", ".")
                                .replace(".class", ""));
                        Class[] interfaces = loadClass.getInterfaces();
                        for (Class i : interfaces
                        ) {
                            if (i.equals(PluginInterface.class)) {
                                loadPlugins.add(loadClass);
                                break;
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                }
            }
        return loadPlugins;
        }
    private ArrayList<String> getClassAndAddArrayList (File file)  {
        ArrayList<String> list = newArrayList();
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert jarFile != null;
        jarFile.stream().forEach(jarEntry -> {
            if (jarEntry.getName().endsWith(".class")) {
                list.add(jarEntry.getName());
            }
        });
        return list;
    }
}




