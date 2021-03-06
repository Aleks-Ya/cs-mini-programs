import config.Config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.System.out;

/**
 * Вызывает команду fetch для git-репозитариев, перечисленных в repo_urls_for_fetch.txt (в отдельных потоках).
 */
class GitFetchAllRepos {
    private Config config;

    public void run() throws IOException, InterruptedException {
        Timer timer = new Timer().start();
        List<File> repos = config.getRepositories();
        if (!repos.isEmpty()) {
            List<Fetcher> fetchers = makeFetchers(repos);
            List<Future<String>> undoneFutures = executeFetchers(fetchers);
            printOutput(undoneFutures);
            timer.finish();
            out.println("Time: " + timer);
        } else {
            out.println("No repos in config");
        }
    }

    private void printOutput(List<Future<String>> undoneFutures) throws InterruptedException {
        while (!undoneFutures.isEmpty()) {
            List<Future<String>> done = new ArrayList<>();
            for (Future<String> future : undoneFutures) {
                try {
                    if (future.isDone()) {
                        done.add(future);
                        out.printf("%s%n", future.get());
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            undoneFutures.removeAll(done);
        }
    }

    private List<Fetcher> makeFetchers(List<File> repos) {
        List<Fetcher> fetchers = new ArrayList<>();
        for (File repoDir : repos) {
            fetchers.add(new Fetcher(repoDir));
        }
        return fetchers;
    }

    private List<Future<String>> executeFetchers(List<Fetcher> fetchers) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(fetchers.size());
        List<Future<String>> futures = es.invokeAll(fetchers);
        es.shutdown();
        return futures;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}