import config.Config;
import config.JarSiblingConfig;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Config config = new JarSiblingConfig("repo_urls_for_fetch.txt");
        GitFetchAllRepos app = new GitFetchAllRepos();
        app.setConfig(config);
        app.run();
    }
}