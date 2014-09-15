import config.Config;
import config.JarSiblingConfig;

import java.io.IOException;

/**
 * todo Наисать инсталлятор под Linux
 * todo Печать версии при запуске
 * todo Определять ситуацию, когда не введен пароль в ssh-add
 * todo Выводить информацию по каждому репозитарию по мере завершения его задачи
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Config config = new JarSiblingConfig("repo_urls_for_fetch.txt");
        GitFetchAllRepos app = new GitFetchAllRepos();
        app.setConfig(config);
        app.run();
    }
}