import config.Config;
import config.WorkingDirConfig;

import java.io.IOException;

/**
 * Вызывает команду fetch для git-репозитариев, перечисленных в repo_urls_for_fetch.txt (в отдельных потоках).
 * todo Убрать из sh-скрипта смену текущей директории. Находить конфиг рядом с jar без исользования текущей директории.
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Config config = new WorkingDirConfig("repo_urls_for_fetch.txt");
        GitFetchAllRepos app = new GitFetchAllRepos();
        app.setConfig(config);
        app.run();
    }
}