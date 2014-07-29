package config;

import java.io.File;

/**
 * Находит конфигурационный файл рядом с исполняемым jar-файлом.
 */
public class JarSiblingConfig extends FileConfig {

    public JarSiblingConfig(String configFileName) {
        super(new File(new File(System.getProperty("sun.java.command")).getParentFile(), configFileName));
    }
}