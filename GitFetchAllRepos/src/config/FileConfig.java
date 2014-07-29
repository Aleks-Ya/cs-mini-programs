package config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * Находит конфигурационный файл в рабочей директории.
 */
public class FileConfig implements Config {
    final Path configPath;

    public FileConfig(File configPath) {
        this.configPath = configPath.toPath();
    }

    public FileConfig(Path configPath) {
        this.configPath = configPath;
    }

    @Override
    public List<File> getRepositories() {
        out.printf("Read config from %s%n", configPath);
        List<File> repos = new ArrayList<>();
        try {
            List<String> repoNames = Files.readAllLines(configPath, Charset.defaultCharset());
            for (String repoName : repoNames) {
                repos.add(new File(repoName));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error read config file " + configPath);
        }
        return repos;
    }
}