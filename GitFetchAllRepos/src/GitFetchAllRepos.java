import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Вызывает команду fetch для git-репозитариев, перечисленных в repo_urls_for_fetch.txt (в отдельных потоках).
 */
public class GitFetchAllRepos {
    private static final File REPO_URLS = new File("repo_urls_for_fetch.txt");

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.printf("Read: %s\n\n", REPO_URLS.getAbsolutePath());
        List<File> repos = readRepos();
        List<Fetcher> fetchers = makeFetchers(repos);
        List<Future<String>> undoneFutures = executeFetchers(fetchers);
        printOutput(undoneFutures);
    }

    private static void printOutput(List<Future<String>> undoneFutures) throws InterruptedException {
        while (!undoneFutures.isEmpty()) {
            List<Future<String>> done = new ArrayList<>();
            for (Future<String> future : undoneFutures) {
                try {
                    if (future.isDone()) {
                        done.add(future);
                        System.out.printf("%s%n", future.get());
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            undoneFutures.removeAll(done);
        }
    }

    private static List<File> readRepos() throws IOException {
        List<File> repos = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(REPO_URLS));
        String line;
        while ((line = reader.readLine()) != null) {
            File repoDir = new File(line);
            if (!repoDir.exists()) {
                throw new IllegalArgumentException("Repo dir doesn't exists: " + repoDir.getAbsolutePath());
            }
            repos.add(repoDir);
        }
        return repos;
    }

    private static List<Fetcher> makeFetchers(List<File> repos) {
        List<Fetcher> fetchers = new ArrayList<>();
        for (File repoDir : repos) {
            fetchers.add(new Fetcher(repoDir));
        }
        return fetchers;
    }

    private static List<Future<String>> executeFetchers(List<Fetcher> fetchers) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(fetchers.size());
        List<Future<String>> futures = es.invokeAll(fetchers);
        es.shutdown();
        return futures;
    }

}