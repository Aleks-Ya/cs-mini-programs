package config;

import java.io.File;
import java.util.List;

/**
 * Конфигурация.
 */
public interface Config {
    List<File> getRepositories();
}