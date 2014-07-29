package config;

import java.io.File;

/**
 * Находит конфигурационный файл в рабочей директории.
 */
public class WorkingDirConfig extends FileConfig {

    public WorkingDirConfig(String configFileName) {
        super(new File(System.getProperty("user.dir"), configFileName));
    }
}