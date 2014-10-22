import config.Config;
import config.JarSiblingConfig;

import java.io.IOException;

/**
 * todo Инсталлятор под Linux
 * todo Определять ситуацию, когда не введен пароль в ssh-add
 * todo Выводить информацию по каждому репозитарию по мере завершения его задачи
 * todo Добавить логирование в файл
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Version version = new Version();
        System.out.println("Version: " + version.getVersion());
        Config config = new JarSiblingConfig("repo_urls_for_fetch.txt");
        GitFetchAllRepos app = new GitFetchAllRepos();
        app.setConfig(config);
        app.run();
    }
}